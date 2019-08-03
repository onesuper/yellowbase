package io.iftech.yellowbase.core.collect;

import com.google.common.truth.Truth;

import org.junit.Test;

public class SimpleTopDocsCollectorTest {

    @Test
    public void testSimpleTopDocsCollector() {

        TopDocsCollector<String> collector = new SimpleTopDocsCollector<String>();

        collector.collect("1", 1);
        collector.collect("2", 2);
        collector.collect("3", 3);

        TopDocs topDocs = collector.topDocs();

        Truth.assertThat(topDocs.totalHits).isEqualTo(3);
        Truth.assertThat(topDocs.scoreDocs).hasLength(3);
        Truth.assertThat(topDocs.scoreDocs).asList().containsExactly(
            new ScoreDoc<>("1", 1),
            new ScoreDoc<>("2", 2),
            new ScoreDoc<>("3", 3));
    }
}