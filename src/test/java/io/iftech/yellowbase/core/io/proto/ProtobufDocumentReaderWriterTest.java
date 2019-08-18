package io.iftech.yellowbase.core.io.proto;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Field;
import io.iftech.yellowbase.core.document.Fields;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.junit.Test;

public class ProtobufDocumentReaderWriterTest {

    @Test
    public void testDocumentWithMultipleFields() throws Exception {

        Field f1 = Fields.as("int", 12345 );
        Field f2 = Fields.as("string", "bob dylan" );
        Field f3 = Fields.as("float", Float.MAX_VALUE);
        Document document = new Document()
            .add(f1)
            .add(f2)
            .add(f3);

        Document document1 = serializeAndThenBack(document);
        Truth.assertThat(document1.getFields().get(0)).isEqualTo(f1);
        Truth.assertThat(document1.getFields().get(1)).isEqualTo(f2);
        Truth.assertThat(document1.getFields().get(2)).isEqualTo(f3);
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