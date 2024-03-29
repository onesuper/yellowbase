package io.iftech.yellowbase.core.index;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.iftech.yellowbase.core.Index;
import io.iftech.yellowbase.core.Segment;
import io.iftech.yellowbase.core.SegmentMeta;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.repository.SegmentRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IndexWriter implements Indexable {

    private static final Logger LOG = LoggerFactory.getLogger(IndexWriter.class);

    /**
     * Put document will block the number of docs waiting in the {@link #indexingQueue} reaches
     * MAX_PENDING_DOCUMENTS
     */
    private static final int MAX_PENDING_DOCUMENTS = 1_000;

    /**
     * The the memory size of a segment exceeds MAX_HEAP_SIZE_IN_BYTES, it will be persisted
     * to disk
     */
    private static final int MAX_HEAP_SIZE_IN_BYTES = 3_000_000;

    private BlockingQueue<Document> indexingQueue;
    
    private Index index;
    private SegmentUpdater segmentUpdater;
    private SegmentRepository segmentRepository;

    // The main thread use this flag to coordinate the index threads to finish commit
    private volatile boolean isCommit;

    // Per Thread memory budget
    private int memoryBudget;
    private int numThreads;
    private IndexWorkers indexWorkers;
    private Consumer<Document> postIndexHandler = null;

    public static IndexWriter newInstance(Index index, int numThreads) {
        return new IndexWriter(index, numThreads,
            MAX_HEAP_SIZE_IN_BYTES, MAX_PENDING_DOCUMENTS);
    }

    @VisibleForTesting
    IndexWriter(Index index, int numThreads, int memoryBudget, int indexQueueSize) {
        this.index = index;
        this.numThreads = numThreads;
        this.memoryBudget = memoryBudget;
        this.segmentRepository = index.getSegmentRepository();
        this.segmentUpdater = new SegmentUpdater(index);
        this.indexWorkers = new IndexWorkers(
            Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("yellowbase-index-%d").build()));

        this.indexingQueue = new ArrayBlockingQueue<>(indexQueueSize);
        isCommit = false;
        startIndexWorkers();
    }

    public void setPostIndexHandler(Consumer<Document> postIndexHandler) {
        this.postIndexHandler = postIndexHandler;
    }

    /**
     * 在缓冲区写满以后这个方法会阻塞
     */
    @Override
    public void addDocument(Document document) {
        try {
            indexingQueue.put(document);
        } catch (InterruptedException ex) {
            throw new RuntimeException("Fail to insert document", ex);
        }
    }

    // Index documents into a segment
    private void indexDocuments(Segment segment, List<Document> documentsToBeIndexed) {
        long start = System.currentTimeMillis();
        SegmentWriter segmentWriter = new SegmentWriter(segment, segmentRepository);
        for (Document doc : documentsToBeIndexed) {
            segmentWriter.addDocument(doc);

            if (postIndexHandler != null) {
                postIndexHandler.accept(doc);
            }

            if (segmentWriter.memoryUsage() > memoryBudget) {
                LOG.info("Exceed memory budget, persist segment");
                break;
            }
        }

        int numDocs = segmentWriter.maxDocs();

        SegmentMeta segmentMeta = SegmentMeta.newInstance(segment.id(), numDocs);
        segmentUpdater.addSegment(new SegmentEntry(segmentMeta));

        LOG.info("It took {} ms to add {} docs to segment {}", System.currentTimeMillis() - start,
            numDocs, segment.id());
    }

    // The infinite index worker loop reads documents from queue
    // and collect them into segmentUpdater
    private void startIndexWorkers() {
        Runnable workerLoop = () -> {
            while (true) {
                List<Document> documentsToBeIndexed = new ArrayList<>();
                indexingQueue.drainTo(documentsToBeIndexed);

                // NOTE: Is it strong enough to say the queue has no data?
                if (documentsToBeIndexed.size() > 0) {
                    Segment segment = index.newSegment();
                    indexDocuments(segment, documentsToBeIndexed);
                } else {
                    if (isCommit) {
                        return;
                    }
                }
            }
        };
        indexWorkers.spawn(workerLoop, numThreads);
    }

    /**
     * All documents added before commit() operation is ensured to be persisted and searchable
     *
     * NOTE: commit() is s blocking call
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        isCommit = true;
        try {
            indexWorkers.join();
        } catch (Exception ex) {
            throw new IOException(ex);
        }
        segmentUpdater.commit();
        isCommit = false;

        startIndexWorkers();
    }
}
