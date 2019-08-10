package io.iftech.yellowbase.core.repository;

import io.iftech.yellowbase.core.IndexMeta;
import io.iftech.yellowbase.core.SegmentMeta;

public interface IndexMetaRepository {
    IndexMeta getIndexMeta();

    void createSegmentMeta(SegmentMeta segmentMeta);
}
