package io.iftech.yellowbase.core.common;

import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("UnstableApiUsage")
public final class CountingOutputStream extends OutputStream {

    private com.google.common.io.CountingOutputStream countingOutputStream;

    public CountingOutputStream(OutputStream outputStream) {
        this.countingOutputStream = new com.google.common.io.CountingOutputStream(outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        this.countingOutputStream.write(b);
    }

    public long getWrittenBytes() {
        return this.countingOutputStream.getCount();
    }
}
