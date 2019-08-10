package io.iftech.yellowbase.core;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.iftech.yellowbase.core.functional.DirectorExecutor;
import io.iftech.yellowbase.core.functional.Executor;
import io.iftech.yellowbase.core.functional.MapIterable;
import io.iftech.yellowbase.core.query.Query;
import io.iftech.yellowbase.core.repository.IndexMetaRepository;
import io.iftech.yellowbase.core.repository.RAMIndexMetaRepository;
import io.iftech.yellowbase.core.search.Searcher;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Index {

    private MapIterable searchExecutor;
    private IndexMetaRepository indexMetaRepository;

    @VisibleForTesting
    Index(IndexMetaRepository indexMetaRepository) {
        this.searchExecutor = new DirectorExecutor();
        this.indexMetaRepository = indexMetaRepository;
    }

    public static Index createInRAM() {
        return new Index(new RAMIndexMetaRepository());
    }

    public List<Segment> getSearchableSegments() {
        return getSegmentMetas().stream().map(this::segment).collect(Collectors.toList());
    }

    public Segment newSegment() {
        SegmentMeta meta = SegmentMeta.newInstance();
        indexMetaRepository.createSegmentMeta(meta);
        return segment(meta);
    }

    private Segment segment(SegmentMeta segmentMeta) {
        return new Segment(this, segmentMeta);
    }

    public List<SegmentMeta> getSegmentMetas() {
        return getIndexMeta().getSegmentMetas();
    }

    public IndexMeta getIndexMeta() {
        return indexMetaRepository.getIndexMeta();
    }

    /**
     * 用于获取并发执行 {@link Searcher#search(Query)} 的线程池
     *
     * 默认不使用线程池，在调用线程中执行
     *
     * @return {@link MapIterable}
     */
    public MapIterable searchExecutor() {
        return searchExecutor;
    }

    public void setNumThreads(int n) {
        this.searchExecutor = new Executor(Executors.newFixedThreadPool(n,
            new ThreadFactoryBuilder().setNameFormat("yellowbase-search-%d").build()));
    }

    public void setDefaultNumThreads() {
        setNumThreads(Runtime.getRuntime().availableProcessors());
    }

    public IndexReader reader() {
        return IndexReader.newBuilder(this).build();
    }
}
