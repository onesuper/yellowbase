package io.iftech.yellowbase.core.collect;

import com.google.common.truth.Truth;
import org.junit.Test;

public class TopDocsTest {

    @Test
    public void mergeMultipleTopDocsIntoOne() {

        TopDocs[] topDocsArray = new TopDocs[]{
            new TopDocs(3,
                new ScoreDoc("4", 4),
                new ScoreDoc("1", 1),
                new ScoreDoc("5", 5)
            ),
            new TopDocs(2,
                new ScoreDoc("2", 2),
                new ScoreDoc("6", 6)
            ),
            new TopDocs(1,
                new ScoreDoc("3", 3)
            )
        };

        TopDocs topDocs = TopDocs.merge(topDocsArray);
        Truth.assertThat(topDocs.totalHits).isEqualTo(6);
        Truth.assertThat(topDocs.scoreDocs).asList().containsExactly(
            new ScoreDoc("6", 6),
            new ScoreDoc("5", 5),
            new ScoreDoc("4", 4),
            new ScoreDoc("3", 3),
            new ScoreDoc("2", 2),
            new ScoreDoc("1", 1)
        );
    }
}