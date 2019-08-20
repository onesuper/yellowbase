package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedOutputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.FieldValue;
import io.iftech.yellowbase.core.io.BinarySerializable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ProtobufDocumentWriter implements BinarySerializable<Document> {

    private CodedOutputStream out;

    public ProtobufDocumentWriter(OutputStream os) {
        this.out = CodedOutputStream.newInstance(os);
    }

    @Override
    public void serialize(Document document) throws IOException {
        this.out.writeSInt32NoTag(document.getFields().size());
        for (FieldValue fieldValue : document.getFields()) {
            writeFieldValue(fieldValue);
        }
    }

    private void writeFieldValue(FieldValue fieldValue) throws IOException {

        out.writeSInt32NoTag(fieldValue.getFieldNumber());
        out.writeSInt32NoTag(fieldValue.getType().getCode());

        Object v = fieldValue.getValue();

        switch (fieldValue.getType()) {
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
                throw new IllegalArgumentException("Unresolved data type: " + fieldValue.getType());
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }
}
