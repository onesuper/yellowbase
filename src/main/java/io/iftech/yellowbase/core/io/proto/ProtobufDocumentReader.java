package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedInputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.FieldValue;
import io.iftech.yellowbase.core.document.Type;
import io.iftech.yellowbase.core.io.BinaryDeserializable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ProtobufDocumentReader implements BinaryDeserializable<Document> {

    private CodedInputStream in;

    public ProtobufDocumentReader(InputStream is) {
        this.in = CodedInputStream.newInstance(is);
    }

    @Override
    public Document deserialize() throws IOException {
        int numFields = this.in.readSInt32();
        Document doc = new Document();
        for (int i = 0; i < numFields; i++) {
            doc.add(readFieldValue());
        }
        return doc;
    }

    private FieldValue readFieldValue() throws IOException {
        String name = in.readString();
        int code = in.readSInt32();
        Type t = Type.parseFromCode(code);
        switch (t) {

            case INT:
                return FieldValue.as(name, in.readSInt32());

            case BIGINT:
                return FieldValue.as(name, in.readSInt64());

            case FLOAT:
                return FieldValue.as(name, in.readFloat());

            case DOUBLE:
                return FieldValue.as(name, in.readDouble());

            case DATETIME:
                return FieldValue.as(name, new Date(in.readSInt64()));

            case STRING:
                int size = in.readRawVarint32();
                byte[] bytes = in.readRawBytes(size);
                return FieldValue.as(name, new String(bytes, StandardCharsets.UTF_8));

            default:
                throw new IllegalArgumentException("Unresolved data type code: " + code);
        }
    }
}
