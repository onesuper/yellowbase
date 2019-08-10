package io.iftech.yellowbase.core.repository;

import io.iftech.yellowbase.core.IndexMeta;
import io.iftech.yellowbase.core.SegmentMeta;
import java.util.LinkedList;
import java.util.List;

public class RAMIndexMetaRepository implements IndexMetaRepository {

    private List<SegmentMeta> segmentMetas = new LinkedList<>();

    @Override
    public IndexMeta getIndexMeta() {
        return new IndexMeta(segmentMetas);
    }

    @Override
    public void createSegmentMeta(SegmentMeta segmentMeta) {
        this.segmentMetas.add(segmentMeta);
    }
}
