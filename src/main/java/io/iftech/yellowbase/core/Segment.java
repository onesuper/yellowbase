package io.iftech.yellowbase.core;

public class Segment {

    Index index; // back-ref

    SegmentMeta segmentMeta;

    public Segment(Index index, SegmentMeta segmentMeta) {
        this.index = index;
        this.segmentMeta = segmentMeta;
    }

    public SegmentMeta meta() {
        return segmentMeta;
    }

    public String id() {
        return this.segmentMeta.id();
    }
}
