package io.iftech.yellowbase.core.postings;

import java.io.IOException;

public interface SkipWriter<DocId> {

    void writeDoc(DocId docId) throws IOException;

    byte[] getBytes() throws IOException;

    void clear();
}
