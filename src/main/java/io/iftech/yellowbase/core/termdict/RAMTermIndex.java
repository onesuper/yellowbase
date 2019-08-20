package io.iftech.yellowbase.core.termdict;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RAMTermIndex<TermKey, TermId> implements TermIndex<TermKey, TermId> {

    Map<TermKey, TermId> termIdByKey = new HashMap<>();

    @Override
    public Optional<TermId> get(TermKey key) {
        return Optional.ofNullable(termIdByKey.get(key));
    }

    @Override
    public void insert(TermKey key, TermId termId) {
        termIdByKey.put(key, termId);
    }
}
