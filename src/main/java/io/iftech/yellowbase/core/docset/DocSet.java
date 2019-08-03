package io.iftech.yellowbase.core.docset;

public interface DocSet<DocId> {

    /**
     * 用来标识移动结果的哨兵字段
     */
    int NO_MORE_DOCS= -1;

    /**
     * 移动到下一个文档
     *
     * return {@value #NO_MORE_DOCS} 如果没有文档了
     */
    int next();

    /**
     * 直接跳到某一个文档后的一个文档
     *
     * return {@value #NO_MORE_DOCS} 如果没有文档了
     */
    int advance(DocId target);

    /**
     * 获取当前文档的 DocId
     */
    DocId docId();

    /**
     * 获取当前文档集合的大小
     */
    long size();
}
