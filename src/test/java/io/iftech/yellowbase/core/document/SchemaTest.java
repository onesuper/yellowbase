package io.iftech.yellowbase.core.document;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.truth.Truth;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        Truth.assertThat(schema.getField("int_1").isPresent()).isTrue();
        Truth.assertThat(schema.getField("int_1").get().getFieldNumber()).isEqualTo(1);

        Truth.assertThat(schema.getField("float_2").isPresent()).isTrue();
        Truth.assertThat(schema.getField("float_2").get().getFieldNumber()).isEqualTo(2);

        Truth.assertThat(schema.getField("datetime_3").isPresent()).isTrue();
        Truth.assertThat(schema.getField("datetime_3").get().getFieldNumber()).isEqualTo(3);
        Truth.assertThat(schema.getField("datetime_33").isPresent()).isFalse();
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
}