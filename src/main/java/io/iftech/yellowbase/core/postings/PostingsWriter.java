package io.iftech.yellowbase.core.postings;

import io.iftech.yellowbase.core.index.Term;

public interface PostingsWriter<DocId> {

    /**
     * 在倒排表中 Term 相应的位置记录文档记录的 ID
     *
     * @param term
     * @param docId
     */
    void subscribe(Term term, DocId docId);
}
