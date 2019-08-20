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
        int fieldNumber = in.readSInt32();
        int code = in.readSInt32();
        switch (Type.parseFromCode(code)) {

            case INT:
                return FieldValue.newInt(fieldNumber, in.readSInt32());

            case BIGINT:
                return FieldValue.newBigInt(fieldNumber, in.readSInt64());

            case FLOAT:
                return FieldValue.newFloat(fieldNumber, in.readFloat());

            case DOUBLE:
                return FieldValue.newDouble(fieldNumber, in.readDouble());

            case DATETIME:
                return FieldValue.newDateTime(fieldNumber, new Date(in.readSInt64()));

            case STRING:
                int size = in.readRawVarint32();
                byte[] bytes = in.readRawBytes(size);
                String s = new String(bytes, StandardCharsets.UTF_8);
                return FieldValue.newString(fieldNumber, s);

            default:
                throw new IllegalArgumentException("Unresolved data type code: " + code);
        }
    }
}
