package io.iftech.yellowbase.core.io.proto;

import com.google.protobuf.CodedOutputStream;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Field;
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
        for (Field field : document.getFields()) {
            writeField(field);
        }
    }

    private void writeField(Field field) throws IOException {

        out.writeStringNoTag(field.getName());
        out.writeSInt32NoTag(field.getType().getCode());

        Object v = field.getValue();

        switch (field.getType()) {
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
                throw new IllegalArgumentException("Unresolved data type: " + field.getType());
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }
}
