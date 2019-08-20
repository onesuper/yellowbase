package io.iftech.yellowbase.core.io.proto;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Field;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import org.junit.Test;

public class ProtobufDocumentReaderWriterTest {

    @Test
    public void testDocumentWithMultipleFields() throws Exception {

        Document document = new Document()
            .addInt(new Field("int", 1), 12345)
            .addString(new Field("string", 2), "bob dylan")
            .addFloat(new Field("float", 3), Float.MAX_VALUE)
            .addDateTime(new Field("datetime", 4), new Date());

        Truth.assertThat(serializeAndThenBack(document)).isEqualTo(document);
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