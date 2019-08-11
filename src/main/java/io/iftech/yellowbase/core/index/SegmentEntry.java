package io.iftech.yellowbase.core.index;

import io.iftech.yellowbase.core.SegmentMeta;

/**
 * 用来在内存中管理每个 segment 的状态
 */
public final class SegmentEntry {

    private SegmentMeta meta;

    public SegmentEntry(SegmentMeta meta) {
        this.meta = meta;
    }

    public String segmentId() {
        return meta.id();
    }

    @Override
    public String toString() {
        return "SegmentEntry(" + meta + ")";
    }
}
