package io.iftech.yellowbase.core.docset;

public final class AllDocSet implements DocSet<Integer> {

    private Integer maxDocId;
    private Integer docId = 0;

    public AllDocSet(Integer maxDocId) {
        this.maxDocId = maxDocId;
    }

    @Override
    public int next() {
        return advance(docId + 1);
    }

    @Override
    public int advance(Integer target) {
        docId = target;
        if (docId >= maxDocId) {
            docId = NO_MORE_DOCS;
        }
        return docId;
    }

    @Override
    public Integer docId() {
        return docId;
    }

    @Override
    public long size() {
        return maxDocId;
    }
}
