package io.iftech.yellowbase.core.collect;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TopDocs {

    public long totalHits;
    public ScoreDoc[] scoreDocs;

    public TopDocs(long totalHits, ScoreDoc... scoreDocs) {
        this.totalHits = totalHits;
        this.scoreDocs = scoreDocs;
    }

    public static TopDocs merge(TopDocs[] shards) {
        int availHitCount = 0;
        long totalHitCount = 0;

        for (TopDocs topDocs : shards) {
            totalHitCount += topDocs.totalHits;
            availHitCount += topDocs.scoreDocs.length;
        }

        PriorityQueue<ScoreDoc> mergeSortQueue = new PriorityQueue<>();

        for (TopDocs shard : shards) {
            mergeSortQueue.addAll(Arrays.asList(shard.scoreDocs));
        }

        ScoreDoc[] hits = new ScoreDoc[availHitCount];

        int idx = 0;
        while (!mergeSortQueue.isEmpty()) {
            ScoreDoc next = mergeSortQueue.remove();
            hits[idx] = next;
            ++idx;
        }
        return new TopDocs(totalHitCount, hits);
    }
}

