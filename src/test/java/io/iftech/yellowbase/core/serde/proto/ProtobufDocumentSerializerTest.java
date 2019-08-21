package io.iftech.yellowbase.core.serde.proto;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Options;
import io.iftech.yellowbase.core.document.Schema;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import org.junit.Test;

public class ProtobufDocumentSerializerTest {

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

        Document document = new Document(schema)
            .addInt("int_1", 12345)
            .addString("string_0", "bob dylan")
            .addFloat("float_2", Float.MAX_VALUE)
            .addDateTime("datetime_5", new Date());

        Truth.assertThat(serializeAndThenBack(document)).isEqualTo(document);
    }

    @Test
    public void fieldNamesCanChange() throws Exception {

        Schema schema2 = Schema.builder()
            .addIntField("String_0", 0, Options.DEFAULT)
            .addIntField("Int_1", 1, Options.DEFAULT)
            .addFloatField("Float_2", 2, Options.INDEXED)
            .addDatetimeField("Datetime_3", 3, Options.STORED)
            .addBigIntField("Bigint_4", 4,  Options.DEFAULT)
            .addDatetimeField("Datetime_5", 5, Options.DEFAULT)
            .addDoubleField("Double_6", 6 ,Options.DEFAULT)
            .build();

        Document document = new Document(schema2)
            .addInt("Int_1", 12345)
            .addString("String_0", "bob dylan")
            .addFloat("Float_2", Float.MAX_VALUE)
            .addDateTime("Datetime_5", new Date());

        Truth.assertThat(serializeAndThenBack(document)).isEqualTo(document);
    }

    private Document serializeAndThenBack(Document document) throws Exception {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ProtobufDocumentSerializer serde = new ProtobufDocumentSerializer(document.getSchema());
        serde.serialize(document, boas);

        InputStream is = new ByteArrayInputStream(boas.toByteArray());
        return serde.deserialize(is);
    }
}