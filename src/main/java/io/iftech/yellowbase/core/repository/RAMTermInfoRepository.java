package io.iftech.yellowbase.core.repository;

import io.iftech.yellowbase.core.postings.TermInfo;
import java.util.ArrayList;
import java.util.List;

public class RAMTermInfoRepository implements TermInfoRepository<Integer> {

    List<TermInfo> termInfos = new ArrayList<>();

    @Override
    public TermInfo get(Integer termId) {
        return termInfos.get(termId);
    }

    @Override
    public void add(TermInfo termInfo) {
        termInfos.add(termInfo);
    }

    @Override
    public int getTotalTerms() {
        return termInfos.size();
    }
}
