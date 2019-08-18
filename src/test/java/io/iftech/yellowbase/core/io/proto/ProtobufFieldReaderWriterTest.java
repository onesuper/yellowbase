package io.iftech.yellowbase.core.io.proto;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.document.Field;
import io.iftech.yellowbase.core.document.Fields;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import org.junit.Test;

public class ProtobufFieldReaderWriterTest {

    @Test
    public void testFloatField() throws Exception {
        Field field = Fields.as("float", Float.MAX_VALUE);
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    @Test
    public void testDoubleField() throws Exception {
        Field field = Fields.as("double", Double.MAX_VALUE);
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    @Test
    public void testStringField() throws Exception {
        Field field = Fields.as("str", "bob dylan");
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    @Test
    public void testIntField() throws Exception {
        Field field = Fields.as("int", Integer.MAX_VALUE);
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    @Test
    public void testBigIntField() throws Exception {
        Field field = Fields.as("bigint", Long.MAX_VALUE);
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    @Test
    public void testDateField() throws Exception {
        Field field = Fields.as("date", new Date());
        Truth.assertThat(serializeAndThenBack(field)).isEqualTo(field);
    }

    private Field serializeAndThenBack(Field field) throws Exception {

        ByteArrayOutputStream boas = new ByteArrayOutputStream();

        ProtobufFieldWriter ser = new ProtobufFieldWriter(boas);
        ser.serialize(field);
        ser.flush();

        InputStream is = new ByteArrayInputStream(boas.toByteArray());
        ProtobufFieldReader de = new ProtobufFieldReader(is);
        return de.deserialize();
    }
}