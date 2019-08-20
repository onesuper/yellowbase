package io.iftech.yellowbase.core.io.proto;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Options;
import io.iftech.yellowbase.core.document.Schema;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import org.junit.Test;

public class ProtobufDocumentReaderWriterTest {

    private Schema schema = Schema.builder()
        .addIntField("string_0", 0, Options.DEFAULT)
        .addIntField("int_1", 1, Options.DEFAULT)
        .addFloatField("float_2", 2, Options.INDEXED)
        .addDatetimeField("datetime_3", 3, Options.STORED)
        .addBigIntField("bigint_4", 4,  Options.DEFAULT)
        .addDatetimeField("datetime_5", 5, Options.DEFAULT)
        .addDoubleField("double_6", 6 ,Options.DEFAULT)
        .build();

    @Test
    public void testDocumentWithMultipleFields() throws Exception {

        Document document = new Document()
            .addInt(schema.getField("int_1").get(), 12345)
            .addString(schema.getField("string_0").get(), "bob dylan")
            .addFloat(schema.getField("float_2").get(), Float.MAX_VALUE)
            .addDateTime(schema.getField("datetime_5").get(), new Date());

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