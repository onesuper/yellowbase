package io.iftech.yellowbase.core.collect;


import com.google.common.base.Objects;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;

public class ScoreDoc<DocId> implements Comparable<ScoreDoc<DocId>> {

    private DocId docId;

    private float score;

    public ScoreDoc(DocId docId, float score) {
        this.docId = docId;
        this.score = score;
    }

    @Override
    public int compareTo(@NotNull ScoreDoc o) {
        if (this.score <= o.score) {
            return 1;
        } else {
            return -1;
        }

    }

    public static class Sorter implements Comparator<ScoreDoc> {
        @Override
        public int compare(ScoreDoc o1, ScoreDoc o2) {
            if (o1.score <= o2.score) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScoreDoc scoreDoc = (ScoreDoc) o;
        return Float.compare(scoreDoc.score, score) == 0 &&
            Objects.equal(docId, scoreDoc.docId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(docId, score);
    }

}
