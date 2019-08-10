package io.iftech.yellowbase.core.search;

import com.google.common.annotations.VisibleForTesting;
import io.iftech.yellowbase.core.Index;
import io.iftech.yellowbase.core.SegmentReader;
import io.iftech.yellowbase.core.collect.Collector;
import io.iftech.yellowbase.core.collect.CollectorReducer;
import io.iftech.yellowbase.core.collect.TopDocs;
import io.iftech.yellowbase.core.collect.TopDocsCollector;
import io.iftech.yellowbase.core.collect.TopicDocsCollectorReducer;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.functional.MapIterable;
import io.iftech.yellowbase.core.query.Query;
import io.iftech.yellowbase.core.query.Scorer;
import io.iftech.yellowbase.core.query.Weight;
import java.util.List;

public final class Searcher implements Searchable {

    private final Index index;
    private final CollectorReducer<TopDocsCollector<Integer>, TopDocs> collectorReducer;
    private final List<SegmentReader> segmentReaders;

    public Searcher(Index index, List<SegmentReader> segmentReaders) {
        this.index = index;
        this.segmentReaders = segmentReaders;
        this.collectorReducer = new TopicDocsCollectorReducer<>();
    }

    /**
     * 搜索过程
     *
     * 1. 为每个 segment 分配一个 {@link Collector}，用来收集文档
     * 2. 每个 segment 都创建一个 {@link Scorer}
     * 3. 将每个命中的文档加入对应的 {@link Collector} 中
     *
     * 以上过程将用线程池并发执行，最终将合并为一个 TopDocs
     */
    @Override
    public SearchResult search(Query query) {
        MapIterable executor = index.searchExecutor();
        TopDocs reducedTopDocs = searchWithExecutor(query, collectorReducer, executor);
        return new SearchResult(reducedTopDocs.totalHits, reducedTopDocs.scoreDocs);
    }

    private <C extends Collector<Integer>, T> T searchWithExecutor(Query query,
        CollectorReducer<C, T> collectorReducer, MapIterable executor) {

        Weight weight = query.weight();

        List<C> collectors = executor.map((segmentReader) -> {
            C collector = collectorReducer.newCollector();
            Scorer scorer = weight.scorer();
            DocSet<Integer> docSet = weight.docSetIterator(segmentReader);
            docSet.foreach((docId) -> collector.collect(docId, scorer.score()));
            return collector;
        }, this.segmentReaders);

        return collectorReducer.reduce(collectors);
    }

    @VisibleForTesting
    public List<SegmentReader> getSegmentReaders() {
        return this.segmentReaders;
    }
}
