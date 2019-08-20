package io.iftech.yellowbase.core.serde.proto;

import com.google.common.base.Preconditions;
import com.google.protobuf.CodedOutputStream;
import io.iftech.yellowbase.core.postings.SkipWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ProtobufSkipWriter implements SkipWriter<Integer> {

    private ByteArrayOutputStream buffer;
    private CodedOutputStream out;
    private int previousDocId;

    public ProtobufSkipWriter() {
        this.buffer = new ByteArrayOutputStream();
        this.out = CodedOutputStream.newInstance(this.buffer);
        this.previousDocId = 0;
    }

    @Override
    public void writeDoc(Integer lastDocId) throws IOException{
        Preconditions.checkState(lastDocId > this.previousDocId);
        int delta = lastDocId - this.previousDocId;
        out.writeSInt32NoTag(delta);
        this.previousDocId = lastDocId;
    }

    @Override
    public byte[] getBytes() throws IOException {
        out.flush();
        return this.buffer.toByteArray();
    }

    @Override
    public void clear() {
        this.buffer = new ByteArrayOutputStream();
        this.previousDocId = 0;
    }
}
