package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.SegmentReader;
import io.iftech.yellowbase.core.docset.DocSet;

public interface Weight {

    Scorer scorer();

    DocSet<Integer> docSetIterator(SegmentReader segmentReader);
}
