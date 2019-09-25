package io.iftech.yellowbase.core.postings;

import io.iftech.yellowbase.core.common.CountingOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 * In charge of serializing underlying and skip list and
 * record the current offset within a {@link OutputStream}
 */
public final class PostingsListWriter implements Closeable {

    private CountingOutputStream countingOut;
    private SkipListWriter<Integer> skipListWriter;

    /**
     * @param out {@link OutputStream} for serializing postings list
     * @param skipListWriter  {@link SkipListWriter} for serializing skip list
     * @param compressOption {@link CompressOption} for compressed the output stream
     */
    public PostingsListWriter(OutputStream out, SkipListWriter<Integer> skipListWriter,
        CompressOption compressOption) {

        OutputStream encodedOut;
        switch (compressOption.algorithm) {
            case ZLIB:
                encodedOut = new DeflaterOutputStream(out);
                break;
            case RAW:
                encodedOut = out;
                break;
            default:
                throw new IllegalArgumentException("Invalid compression option");
        }

        this.countingOut = new CountingOutputStream(encodedOut);
        this.skipListWriter = skipListWriter;
    }

    public void writeDoc(Integer docId) throws IOException {
        this.skipListWriter.writeDoc(docId);
    }

    public long currentOffset() {
        return countingOut.getWrittenBytes();
    }

    public void clear() {
        this.skipListWriter.clear();
    }

    @Override
    public void close() throws IOException {
        countingOut.close();
    }
}
