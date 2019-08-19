package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedInputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Field;
import io.iftech.yellowbase.core.document.Fields.BigIntField;
import io.iftech.yellowbase.core.document.Fields.DateTimeField;
import io.iftech.yellowbase.core.document.Fields.DoubleField;
import io.iftech.yellowbase.core.document.Fields.FloatField;
import io.iftech.yellowbase.core.document.Fields.IntegerField;
import io.iftech.yellowbase.core.document.Fields.StringField;
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
            doc.add(readField());
        }
        return doc;
    }

    private Field readField() throws IOException {
        String name = in.readString();
        int code = in.readSInt32();
        switch (Type.parseFromCode(code)) {

            case INT:
                return new IntegerField(name, in.readSInt32());

            case BIGINT:
                return new BigIntField(name, in.readSInt64());

            case FLOAT:
                return new FloatField(name, in.readFloat());

            case DOUBLE:
                return new DoubleField(name, in.readDouble());

            case DATETIME:
                return new DateTimeField(name, new Date(in.readSInt64()));

            case STRING:
                int size = in.readRawVarint32();
                byte[] bytes = in.readRawBytes(size);
                return new StringField(name, new String(bytes, StandardCharsets.UTF_8));

            default:
                throw new IllegalArgumentException("Unresolved data type code: " + code);
        }
    }
}
