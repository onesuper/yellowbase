package io.iftech.yellowbase.core.docset;

import com.google.common.base.Preconditions;

public final class EmptyDocSet implements DocSet<Integer> {

    private boolean exhausted = false;

    @Override
    public boolean next() {
        Preconditions.checkArgument(!exhausted);
        exhausted = true;
        return false;
    }

    @Override
    public boolean advance(Integer target) {
        Preconditions.checkArgument(!exhausted);
        exhausted = true;
        return false;
    }

    @Override
    public Integer docId() {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }
}
