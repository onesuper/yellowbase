package io.iftech.yellowbase.core.termdict;

import java.util.Optional;

public interface TermIndex<TermKey, TermId> {

    Optional<TermId> get(TermKey key);

    void insert(TermKey key, TermId termId);
}
