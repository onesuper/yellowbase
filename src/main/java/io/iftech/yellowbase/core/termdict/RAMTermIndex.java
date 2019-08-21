package io.iftech.yellowbase.core.termdict;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RAMTermIndex implements TermIndex<byte[], Integer> {

    Map<byte[], Integer> termIdByKey = new HashMap<>();

    @Override
    public Optional<Integer> get(byte[] key) {
        return Optional.ofNullable(termIdByKey.get(key));
    }

    @Override
    public void insert(byte[] key, Integer termId) {
        termIdByKey.put(key, termId);
    }
}
