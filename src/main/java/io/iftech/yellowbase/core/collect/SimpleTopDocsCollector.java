package io.iftech.yellowbase.core.collect;

import com.google.common.collect.MinMaxPriorityQueue;

public class SimpleTopDocsCollector<DocId> implements TopDocsCollector<DocId> {

    private final MinMaxPriorityQueue<ScoreDoc<DocId>> heap;
    private int totalHits;

    public SimpleTopDocsCollector() {
        this.heap = MinMaxPriorityQueue.orderedBy(new ScoreDoc.Sorter()).create();
        this.totalHits = 0;
    }

    @Override
    public void collect(DocId docId) {
        heap.add(new ScoreDoc<>(docId, 0));
        totalHits++;
    }

    @Override
    public TopDocs topDocs() {
        return new TopDocs(totalHits, populateResults(heap.size()));
    }

    private ScoreDoc[] populateResults(int howMany) {
        ScoreDoc[] results = new ScoreDoc[howMany];
        for (int i = howMany - 1; i >= 0; i--) {
            results[i] = heap.pollLast();
        }
        return results;
    }

    public int getTotalHits() {
        return totalHits;
    }
}
