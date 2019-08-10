package io.iftech.yellowbase.core;

import io.iftech.yellowbase.core.search.Searcher;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IndexReader {

    private static final Logger LOG = LoggerFactory.getLogger(IndexReader.class);

    private final Index index;
    private final AtomicReference<List<SegmentReader>> segmentReaders = new AtomicReference<>();

    private IndexReader(Index index) {
        this.index = index;
    }

    public void reload() {
        long start = System.currentTimeMillis();
        List<SegmentReader> segments = index.getSearchableSegments().stream()
            .map(SegmentReader::open)
            .collect(Collectors.toList());
        segmentReaders.set(segments);

        LOG.info("It took {} ms to reload IndexReader", System.currentTimeMillis() - start);
    }

    public Searcher searcher() {
        return new Searcher(index, segmentReaders.get());
    }

    // Builder

    public static IndexReaderBuilder newBuilder(Index index) {
        return new IndexReaderBuilder(index);
    }

    public static class IndexReaderBuilder {

        private Index index;

        private IndexReaderBuilder(Index index) {
            this.index = index;
        }

        public IndexReader build() {
            return new IndexReader(index);
        }
    }
}
