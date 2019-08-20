package io.iftech.yellowbase.core.document;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Schema.SchemaBuilder;
import org.junit.Test;

public class SchemaTest {

    @Test
    public void testSchemaBuildRightSchema() {

        SchemaBuilder builder = Schema.builder()
            .addIntField("int", Options.DEFAULT)
            .addFloatField("float", Options.INDEXED)
            .addDatetimeField("datetime", Options.STORED);

        Schema schema = builder.build();

        FieldEntry f1 = schema.getFieldEntry("int").get();
        Truth.assertThat(f1.getType()).isEqualTo(Type.INT);
        Truth.assertThat(f1.getOptions().isIndexed()).isTrue();
        Truth.assertThat(f1.getOptions().isStored()).isTrue();

        FieldEntry f2 = schema.getFieldEntry("float").get();
        Truth.assertThat(f2.getType()).isEqualTo(Type.FLOAT);
        Truth.assertThat(f2.getOptions().isStored()).isFalse();

        FieldEntry f3 = schema.getFieldEntry("datetime").get();
        Truth.assertThat(f3.getOptions().isIndexed()).isFalse();
        Truth.assertThat(f3.getType()).isEqualTo(Type.DATETIME);
    }

}