package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedInputStream;
import io.iftech.yellowbase.core.postings.SkipReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public final class ProtobufSkipReader implements SkipReader<Integer> {

    private CodedInputStream in;
    private int docId;

    public ProtobufSkipReader(byte[] data) {
        this.in = CodedInputStream.newInstance(new ByteArrayInputStream(data));
        this.docId = 0;
    }

    @Override
    public Integer docId() {
        return this.docId;
    }

    @Override
    public boolean advance() throws IOException {
        if (in.isAtEnd()) {
            return false;
        } else {
            int delta = in.readSInt32();
            this.docId += delta;
        }
        return true;
    }
}
