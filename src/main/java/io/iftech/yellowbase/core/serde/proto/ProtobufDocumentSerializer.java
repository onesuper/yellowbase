package io.iftech.yellowbase.core.serde.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.FieldType;
import io.iftech.yellowbase.core.document.FieldValue;
import io.iftech.yellowbase.core.document.Schema;
import io.iftech.yellowbase.core.serde.BinarySerializable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ProtobufDocumentSerializer implements BinarySerializable<Document> {

    private Schema schema;

    public ProtobufDocumentSerializer(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void serialize(Document document, OutputStream outputStream) throws IOException {
        CodedOutputStream out = CodedOutputStream.newInstance(outputStream);
        out.writeSInt32NoTag(document.getFields().size());
        for (FieldValue fieldValue : document.getFields()) {
            writeFieldValue(out, fieldValue);
        }
        out.flush();
    }

    @Override
    public Document deserialize(InputStream inputStream) throws IOException {
        CodedInputStream in = CodedInputStream.newInstance(inputStream);
        int numFields = in.readSInt32();
        Document doc = new Document(schema);
        for (int i = 0; i < numFields; i++) {
            doc.add(readFieldValue(in));
        }
        return doc;
    }

    private FieldValue readFieldValue(CodedInputStream in) throws IOException {
        int fieldNumber = in.readSInt32();
        int code = in.readSInt32();
        switch (FieldType.parseFromCode(code)) {

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

    private void writeFieldValue(CodedOutputStream out, FieldValue fieldValue) throws IOException {

        out.writeSInt32NoTag(fieldValue.getFieldNumber());
        out.writeSInt32NoTag(fieldValue.getFieldType().getCode());

        Object v = fieldValue.getValue();

        switch (fieldValue.getFieldType()) {
            case INT:
                out.writeSInt32NoTag((Integer) v);
                break;

            case BIGINT:
                out.writeSInt64NoTag((Long) v);
                break;

            case FLOAT:
                out.writeFloatNoTag((Float) v);
                break;

            case DOUBLE:
                out.writeDoubleNoTag((Double) v);
                break;

            case DATETIME:
                Date date = (Date) v;
                long longVal = date.getTime();
                out.writeSInt64NoTag(longVal);
                break;

            case STRING:
                String s = (String) v;
                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                out.writeRawVarint32(bytes.length);
                out.writeRawBytes(bytes);
                break;
            default:
                throw new IllegalArgumentException("Unresolved data type: " + fieldValue.getFieldType());
        }
    }
}
