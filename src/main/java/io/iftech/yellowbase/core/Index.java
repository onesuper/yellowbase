package io.iftech.yellowbase.core;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.iftech.yellowbase.core.common.DirectExecutor;
import io.iftech.yellowbase.core.common.Executor;
import io.iftech.yellowbase.core.common.ParallelExecutor;
import io.iftech.yellowbase.core.index.IndexWriter;
import io.iftech.yellowbase.core.query.Query;
import io.iftech.yellowbase.core.repository.IndexMetaRepository;
import io.iftech.yellowbase.core.repository.RAMIndexMetaRepository;
import io.iftech.yellowbase.core.repository.RAMSegmentRepository;
import io.iftech.yellowbase.core.repository.SegmentRepository;
import io.iftech.yellowbase.core.search.Searcher;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Index {

    private Executor searchExecutor;
    private IndexMetaRepository indexMetaRepository;
    private SegmentRepository segmentRepository;

    private Index(IndexMetaRepository indexMetaRepository, SegmentRepository segmentRepository) {
        this.searchExecutor = new DirectExecutor();
        this.indexMetaRepository = indexMetaRepository;
        this.segmentRepository = segmentRepository;
    }

    @VisibleForTesting
    public static Index create(IndexMetaRepository indexMetaRepository,
        SegmentRepository segmentRepository) {
        return new Index(indexMetaRepository, segmentRepository);
    }

    @VisibleForTesting
    public static Index createInRAM() {
        return new Index(new RAMIndexMetaRepository(), new RAMSegmentRepository());
    }

    /**
     * @return 当前所有可被搜索的 {@link Segment}
     */
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

    /**
     * @return 当前所有可被搜索的 {@link SegmentMeta}
     */
    public List<SegmentMeta> getSegmentMetas() {
        return getIndexMeta().getSegmentMetas();
    }

    public IndexMeta getIndexMeta() {
        return indexMetaRepository.getIndexMeta();
    }

    public SegmentRepository getSegmentRepository() {
        return this.segmentRepository;
    }

    public IndexMetaRepository getIndexMetaRepository() {
        return this.indexMetaRepository;
    }

    /**
     * 用于获取并发执行 {@link Searcher#search(Query)} 的线程池
     *
     * 默认不使用线程池，在调用线程中执行
     *
     * @return {@link Executor}
     */
    public Executor searchExecutor() {
        return searchExecutor;
    }

    public IndexReader reader() {
        return IndexReader.newBuilder(this).build();
    }

    public IndexWriter writer(int numThreads) {
        return IndexWriter.newInstance(this, numThreads);
    }

    // Setters

    public void setSearchThreads(int n) {
        this.searchExecutor = new ParallelExecutor(Executors.newFixedThreadPool(n,
            new ThreadFactoryBuilder().setNameFormat("yellowbase-search-%d").build()));
    }

    public void setDefaultSearchThreads() {
        setSearchThreads(Runtime.getRuntime().availableProcessors());
    }
}
