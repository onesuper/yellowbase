package io.iftech.yellowbase.core;

public final class SegmentReader {

    private int maxDocs;
    private String segmentId;

    public SegmentReader(Segment segment) {
        this.maxDocs = segment.segmentMeta.maxDocs();
        this.segmentId = segment.id();
    }

    public int maxDocs() {
        return this.maxDocs;
    }

    public String id() {
        return segmentId;
    }

    public static SegmentReader open(Segment segment) {
        return new SegmentReader(segment);
    }
}
