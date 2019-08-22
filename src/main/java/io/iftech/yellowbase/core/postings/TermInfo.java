package io.iftech.yellowbase.core.postings;

public class TermInfo {

    private long postingsOffset;

    public TermInfo(long postingsOffset) {
        this.postingsOffset = postingsOffset;
    }

    public long getPostingsOffset() {
        return postingsOffset;
    }
}
