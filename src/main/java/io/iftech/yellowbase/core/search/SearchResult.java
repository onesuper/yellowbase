package io.iftech.yellowbase.core.search;

import io.iftech.yellowbase.core.collect.ScoreDoc;

public class SearchResult {
    private long totalHits;
    private ScoreDoc[] scoreDocs;

    public SearchResult(long totalHits, ScoreDoc[] scoreDocs) {
        this.totalHits = totalHits;
        this.scoreDocs = scoreDocs;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public ScoreDoc[] getScoreDocs() {
        return scoreDocs;
    }
}
