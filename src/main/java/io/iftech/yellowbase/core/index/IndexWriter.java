package io.iftech.yellowbase.core.index;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.iftech.yellowbase.core.Index;
import io.iftech.yellowbase.core.Segment;
import io.iftech.yellowbase.core.SegmentMeta;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.repository.SegmentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
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
     * 当一个线程内存中 segment 大小超过这个值时，会进行持久化
     */
    private static final int MAX_HEAP_SIZE_IN_BYTES = 1_000_000;

    private BlockingDeque<Document> indexingQueue;

    private volatile boolean stop = false;

    private Index index;
    private SegmentUpdater segmentUpdater;
    private SegmentRepository segmentRepository;

    /**
     * Per Thread memory budget
     */
    private int memoryBudget;

    private int numThreads;
    private AsyncWorkers indexWorkers;

    public static IndexWriter newInstance(Index index,int numThreads) {
        IndexWriter indexWriter = new IndexWriter(index, numThreads, MAX_HEAP_SIZE_IN_BYTES);
        indexWriter.reset();
        return indexWriter;
    }

    private IndexWriter(Index index, int numThreads, int memoryBudget) {
        this.index = index;
        this.numThreads = numThreads;
        this.memoryBudget = memoryBudget;
        this.segmentRepository = index.getSegmentRepository();
        this.segmentUpdater = new SegmentUpdater(index);
        this.indexWorkers = new AsyncWorkers(
            Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("yellowbase-index-%d").build()));
    }

    @Override
    public void addDocument(Document document) {
        this.indexingQueue.offer(document);
    }

    // The infinite index worker loop
    // reads documents from queue and collect them into segmentUpdater
    private void workerLoop() {
        while (!stop) {
            List<Document> documentsToBeIndexed = new ArrayList<>();
            this.indexingQueue.drainTo(documentsToBeIndexed);
            Segment segment = index.newSegment();

            if (documentsToBeIndexed.size() > 0) {
                indexDocuments(segment, documentsToBeIndexed);
            }
        }
    }

    // Index documents into a segment
    private void indexDocuments(Segment segment, List<Document> documentsToBeIndexed) {
        long start = System.currentTimeMillis();
        SegmentWriter segmentWriter = new SegmentWriter(segment, segmentRepository);
        for (Document doc : documentsToBeIndexed) {
            segmentWriter.addDocument(doc);

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

    private void reset() {
        this.indexingQueue = new LinkedBlockingDeque<>(MAX_PENDING_DOCUMENTS);
        this.stop = false;
        this.indexWorkers.spawn(this::workerLoop, numThreads);
    }

    /**
     * 将用户调用 commit 之前异步索引的文档，进行提交
     */
    public void commit() {
        stop = true;
        this.indexWorkers.join();
        this.segmentUpdater.commit();
        this.reset();
    }
}
