package io.iftech.yellowbase.core.postings;

import java.io.IOException;

public interface SkipListReader<DocId> {
    DocId docId();

    /**
     * @return false if the skip list reaches the end
     * @throws IOException
     */
    boolean next() throws IOException;
}
