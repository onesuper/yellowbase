package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedOutputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Field;
import io.iftech.yellowbase.core.io.BinarySerializable;
import java.io.IOException;
import java.io.OutputStream;

public class ProtobufDocumentWriter implements BinarySerializable<Document> {

    private ProtobufFieldWriter writer;

    private CodedOutputStream out;

    public ProtobufDocumentWriter(OutputStream os) {
        this.out = CodedOutputStream.newInstance(os);
        this.writer = new ProtobufFieldWriter(this.out);
    }

    @Override
    public void serialize(Document document) throws IOException {
        this.out.writeSInt32NoTag(document.getFields().size());
        for (Field field : document.getFields()) {
            writer.serialize(field);
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }
}
