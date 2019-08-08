package io.iftech.yellowbase.core.query.combiner;

import io.iftech.yellowbase.core.query.Scorer;

public class SumCombiner implements ScoreCombiner {

    private float score;

    @Override
    public void combine(Scorer scorer) {
        score += scorer.score();

    }

    @Override
    public float score() {
        return score;
    }
}
