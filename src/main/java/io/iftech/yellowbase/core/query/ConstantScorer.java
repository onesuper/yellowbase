package io.iftech.yellowbase.core.query;

public class ConstantScorer implements Scorer {

    private final float score;

    public ConstantScorer(float score) {
        this.score = score;
    }

    @Override
    public float score() {
        return score;
    }
}
