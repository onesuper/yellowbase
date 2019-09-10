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
import io.iftech.yellowbase.core.functional.MapExecutor;
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

    @Override
    public SearchResult search(Query query) {
        MapExecutor executor = index.searchExecutor();
        TopDocs reducedTopDocs = searchWithExecutor(query, collectorReducer, executor);
        return new SearchResult(reducedTopDocs.totalHits, reducedTopDocs.scoreDocs);
    }

    /**
     * The search process will be executed concurrently using a thread pool.
     *
     * 1. Assign each segment a {@link Collector} to collect documents.
     *
     * 2. Create a {@link Scorer} for each segment using {@link Weight}.
     *
     * 3. Add the matching docs to its corresponding {@link Collector}. All the collectors are
     *    reduced into a {@link TopDocs} using a {@link CollectorReducer}.
     */
    private <C extends Collector<Integer>, T> T searchWithExecutor(Query query,
        CollectorReducer<C, T> collectorReducer, MapExecutor executor) {

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
