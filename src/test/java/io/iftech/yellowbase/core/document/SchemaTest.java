package io.iftech.yellowbase.core.document;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Schema.SchemaBuilder;
import org.junit.Test;

public class SchemaTest {

    @Test
    public void testSchemaBuildRightSchema() {

        SchemaBuilder builder = Schema.builder()
            .addIntField("int", Option.DEFAULT)
            .addFloatField("float", Option.INDEXED)
            .addDatetimeField("datetime", Option.STORED);

        Schema schema = builder.build();

        FieldEntry f1 = schema.getFieldEntry("int").get();
        Truth.assertThat(f1.getType()).isEqualTo(Type.INT);
        Truth.assertThat(f1.getOption().isIndexed()).isTrue();
        Truth.assertThat(f1.getOption().isStored()).isTrue();

        FieldEntry f2 = schema.getFieldEntry("float").get();
        Truth.assertThat(f2.getType()).isEqualTo(Type.FLOAT);
        Truth.assertThat(f2.getOption().isStored()).isFalse();

        FieldEntry f3 = schema.getFieldEntry("datetime").get();
        Truth.assertThat(f3.getOption().isIndexed()).isFalse();
        Truth.assertThat(f3.getType()).isEqualTo(Type.DATETIME);
    }

}