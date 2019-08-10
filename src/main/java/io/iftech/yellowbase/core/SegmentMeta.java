package io.iftech.yellowbase.core;

import java.util.UUID;

public final class SegmentMeta {

    private String segmentId;

    private int maxDocs;

    SegmentMeta(String segmentId, int maxDocs) {
        this.segmentId = segmentId;
        this.maxDocs = maxDocs;
    }

    public static SegmentMeta newInstance() {
        return new SegmentMeta(UUID.randomUUID().toString(), 0);
    }

    public String id() {
        return segmentId;
    }

    public int maxDocs() {
        return maxDocs;
    }
}
