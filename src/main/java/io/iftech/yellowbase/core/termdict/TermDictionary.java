package io.iftech.yellowbase.core.termdict;

import io.iftech.yellowbase.core.postings.TermInfo;
import io.iftech.yellowbase.core.repository.TermInfoRepository;
import java.util.Optional;

public final class TermDictionary {

    private final TermIndex<byte[], Integer> termIndex;

    private final TermInfoRepository<Integer> termInfoRepository;

    private int termId;

    public TermDictionary(
        TermInfoRepository<Integer> termInfoRepository,
        TermIndex<byte[], Integer> termIndex) {
        this.termInfoRepository = termInfoRepository;
        this.termIndex = termIndex;
        this.termId = 0;
    }

    public void insert(byte[] key, TermInfo termInfo) {
        this.termIndex.insert(key, termId);
        this.termInfoRepository.add(termInfo);
        termId += 1;
    }

    public Optional<TermInfo> getOptionTermInfo(byte[] key) {
        return termIndex.get(key).map(this::getTermInfo);
    }

    public int getTotalTerms() {
        return termInfoRepository.getTotalTerms();
    }

    private TermInfo getTermInfo(Integer termId) {
        return termInfoRepository.get(termId);
    }
}
