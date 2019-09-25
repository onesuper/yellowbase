package io.iftech.yellowbase.core.postings;

import io.iftech.yellowbase.core.termdict.TermDictionary;
import java.io.Closeable;
import java.io.IOException;

/**
 * FieldWriter updates the documents to index and maintain term dictionary for a field.
 *
 * insertTerm(...);
 * writeDoc(1);
 * writeDoc(2);
 * writeDoc(3);
 * insertTerm(...);
 * writeDoc(4);
 * writeDoc(5);
 * writeDoc(6);
 * insertTerm(...);
 * writeDoc(7);
 * writeDoc(8);
 * writeDoc(9);
 */
public final class FieldWriter implements Closeable {

    private TermDictionary termDictionary;
    private PostingsListWriter postingsListWriter;
    private int totalTerms;

    public FieldWriter(TermDictionary termDictionary,
        PostingsListWriter postingsListWriter) {
        this.termDictionary = termDictionary;
        this.postingsListWriter = postingsListWriter;
        this.totalTerms = 0;
    }

    public int insertTerm(byte[] term) {
        termDictionary.insertTerm(term, getCurrentTermInfo());
        postingsListWriter.clear();
        int termId = totalTerms;
        totalTerms += 1;
        return termId;
    }

    public void writeDoc(Integer docId) throws IOException {
        postingsListWriter.writeDoc(docId);
    }

    private TermInfo getCurrentTermInfo() {
        return new TermInfo(this.postingsListWriter.currentOffset());
    }

    @Override
    public void close() throws IOException {
        postingsListWriter.close();
    }
}
