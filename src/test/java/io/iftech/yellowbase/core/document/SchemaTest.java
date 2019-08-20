package io.iftech.yellowbase.core.document;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.truth.Truth;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import org.junit.Test;

public class SchemaTest {

    private Schema schema = Schema.builder()
        .addIntField("int_1", 1, Options.DEFAULT)
        .addFloatField("float_2", 2, Options.INDEXED)
        .addDatetimeField("datetime_3", 3, Options.STORED)
        .addBigIntField("bigint_4", 4,  Options.DEFAULT)
        .addDatetimeField("datetime_5", 5, Options.DEFAULT)
        .addDoubleField("double_6", 6 ,Options.DEFAULT)
        .build();

    @Test
    public void testGetField() {
        Truth.assertThat(schema.getField("int_1").getFieldNumber()).isEqualTo(1);
        Truth.assertThat(schema.getField("float_2").getFieldNumber()).isEqualTo(2);
        Truth.assertThat(schema.getField("datetime_3").getFieldNumber()).isEqualTo(3);
    }

    @Test
    public void testPrettyJsonEqual() throws Exception {
        InputStream is = this.getClass().getResourceAsStream("/schema.json");
        String expect = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
        Truth.assertThat(schema.toJson(true)).isEqualTo(expect);
    }

    @Test
    public void testSerializeJsonThenBack() {
        Truth.assertThat(Schema.fromJson(schema.toJson(false))).isEqualTo(schema);
    }

    @Test
    public void documentCanConvertToJson() {

        Document oldDocument = new Document(schema)
            .addDateTime("datetime_5", new Date())
            .addFloat("float_2", 0.134F)
            .addInt("int_1", Integer.MAX_VALUE);

        Schema newSchema = Schema.builder()
            .addIntField("Int_1", 1, Options.DEFAULT)
            .addFloatField("Float_2", 2, Options.INDEXED)
            .build();

        Truth.assertThat(oldDocument.toJson(newSchema)).isEqualTo("{\"Int_1\":2147483647,\"Float_2\":0.134}");
    }
}