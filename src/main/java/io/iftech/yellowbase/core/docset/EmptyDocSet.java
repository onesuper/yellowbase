package io.iftech.yellowbase.core.docset;

import com.google.common.base.Preconditions;

public final class EmptyDocSet implements DocSet<Integer> {

    private boolean exhausted = false;

    @Override
    public int next() {
        Preconditions.checkArgument(!exhausted);
        exhausted = true;
        return NO_MORE_DOCS;
    }

    @Override
    public int advance(Integer target) {
        Preconditions.checkArgument(!exhausted);
        exhausted = true;
        return NO_MORE_DOCS;
    }

    @Override
    public Integer docId() {
        return exhausted ? NO_MORE_DOCS : -1;
    }

    @Override
    public long size() {
        return 0;
    }
}
