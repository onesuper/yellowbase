package io.iftech.yellowbase.core.docset;

public final class AllDocSet implements DocSet<Integer> {

    private Integer maxDocId;
    private Integer docId = -1;

    public AllDocSet(Integer maxDocId) {
        this.maxDocId = maxDocId;
    }

    @Override
    public boolean next() {
        return advance(docId + 1);
    }

    @Override
    public boolean advance(Integer target) {
        docId = target;
        return maxDocId > docId;
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
