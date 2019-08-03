package io.iftech.yellowbase.core.collect;

public interface Collector<DocId> {

    /**
     * 每次在 document 命中 query 时被调用
     *
     * 例如在一些实现中 Document ID 可以以 BitSet 的形式进行收集
     *
     * @param docId 文档 id
     * @param score 得分
     */
    void collect(DocId docId, float score);
}
