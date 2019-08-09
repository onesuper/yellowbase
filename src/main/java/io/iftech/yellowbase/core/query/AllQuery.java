package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.SegmentReader;
import io.iftech.yellowbase.core.docset.AllDocSet;
import io.iftech.yellowbase.core.docset.DocSet;

public class AllQuery extends Query {

    public AllQuery() {
    }

    @Override
    public void accept(QueryVisitor visitor) {

    }

    @Override
    public Weight weight() {
        return new AllWeight();
    }

    private class AllWeight implements Weight {
        @Override
        public Scorer scorer() {
            return new ConstantScorer(1);
        }

        @Override
        public DocSet<Integer> docSet(SegmentReader segmentReader) {
            return new AllDocSet(segmentReader.maxDocs());
        }
    }

}
