package io.iftech.yellowbase.core.repository;

import io.iftech.yellowbase.core.postings.TermInfo;

public interface TermInfoRepository<TermId> {

    TermInfo get(TermId termId);

    void add(TermInfo termInfo);

    int getTotalTerms();
}
