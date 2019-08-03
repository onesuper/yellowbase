package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.docset.DocSet;

public interface Scorer {

    float score();

    DocSet<Integer> docSet();
}
