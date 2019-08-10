package io.iftech.yellowbase.core;

import java.util.List;

public final class IndexMeta {

    private List<SegmentMeta> segmentMetas;

    public IndexMeta(List<SegmentMeta> segmentMetas) {
        this.segmentMetas = segmentMetas;
    }

    public List<SegmentMeta> getSegmentMetas() {
        return segmentMetas;
    }
}
