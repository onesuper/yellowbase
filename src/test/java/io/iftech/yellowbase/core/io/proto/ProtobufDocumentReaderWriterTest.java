package io.iftech.yellowbase.core.io.proto;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.FieldValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.junit.Test;

public class ProtobufDocumentReaderWriterTest {

    @Test
    public void testDocumentWithMultipleFields() throws Exception {

        List<FieldValue> fields = ImmutableList.of(
            FieldValue.as("int", 12345),
            FieldValue.as("string", "bob dylan"),
            FieldValue.as("float", Float.MAX_VALUE),
            FieldValue.as("double", Double.MAX_VALUE),
            FieldValue.as("date", new Date())
        );

        Document document = new Document();
        fields.forEach(document::add);
        Document result = serializeAndThenBack(document);

        Streams.zip(document.getFields().stream(), result.getFields().stream(),
            (o, i) -> {
                Truth.assertThat(o).isEqualTo(i);
                return 0;
            }
        );
    }

    private Document serializeAndThenBack(Document document) throws Exception {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ProtobufDocumentWriter ser = new ProtobufDocumentWriter(boas);
        ser.serialize(document);
        ser.flush();

        InputStream is = new ByteArrayInputStream(boas.toByteArray());
        ProtobufDocumentReader de = new ProtobufDocumentReader(is);
        return de.deserialize();
    }
}