package io.iftech.yellowbase.core.serde.proto;

import com.google.protobuf.CodedInputStream;
import io.iftech.yellowbase.core.postings.SkipListReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public final class ProtobufSkipListReader implements SkipListReader<Integer> {

    private CodedInputStream in;
    private int docId;

    public ProtobufSkipListReader(byte[] data) {
        this.in = CodedInputStream.newInstance(new ByteArrayInputStream(data));
        this.docId = 0;
    }

    @Override
    public Integer docId() {
        return this.docId;
    }

    @Override
    public boolean next() throws IOException {
        if (in.isAtEnd()) {
            return false;
        } else {
            int delta = in.readSInt32();
            this.docId += delta;
        }
        return true;
    }
}
