package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.docset.DocSet;
import java.util.function.BiConsumer;

public interface Scorer {

    float score();

    DocSet<Integer> docSet();

    default void foreach(BiConsumer<Integer, Float> callback) {
        DocSet<Integer> ds = docSet();
        while (ds.next() != DocSet.NO_MORE_DOCS) {
            callback.accept(ds.docId(), score());
        }
    }
}
