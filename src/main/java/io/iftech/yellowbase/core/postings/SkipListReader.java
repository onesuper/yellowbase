package io.iftech.yellowbase.core.postings;

import java.io.IOException;

public interface SkipListReader<DocId> {
    DocId docId();

    boolean next() throws IOException;
}
