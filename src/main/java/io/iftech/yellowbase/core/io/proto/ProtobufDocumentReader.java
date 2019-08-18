package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedInputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.io.BinaryDeserializable;
import java.io.IOException;
import java.io.InputStream;

public class ProtobufDocumentReader implements BinaryDeserializable<Document> {

    private ProtobufFieldReader reader;

    private CodedInputStream in;

    public ProtobufDocumentReader(InputStream is) {
        this.in = CodedInputStream.newInstance(is);
        this.reader = new ProtobufFieldReader(this.in);
    }

    @Override
    public Document deserialize() throws IOException {
        int numFields = this.in.readSInt32();
        Document doc = new Document();
        for (int i = 0; i < numFields; i++) {
            doc.add(reader.deserialize());
        }
        return doc;
    }
}
