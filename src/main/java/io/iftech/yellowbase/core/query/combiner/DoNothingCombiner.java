package io.iftech.yellowbase.core.query.combiner;

import io.iftech.yellowbase.core.query.Scorer;

public class DoNothingCombiner implements ScoreCombiner {

    @Override
    public void combine(Scorer scorer) {
    }

    @Override
    public float score() {
        return 0;
    }
}
