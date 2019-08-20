package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import java.util.Date;
import java.util.Objects;

public class FieldValue {

    private int fieldNumber;
    private Type type;
    private Object value;

    FieldValue(int fieldNumber, Type type, Object value) {
        this.fieldNumber = fieldNumber;
        this.type = Preconditions.checkNotNull(type);
        this.value = Preconditions.checkNotNull(value);
    }

    public static FieldValue newInt(int fieldNumber, int value) {
        return new FieldValue(fieldNumber, Type.INT, value);
    }

    public static FieldValue newBigInt(int fieldNumber, long value) {
        return new FieldValue(fieldNumber, Type.BIGINT, value);
    }

    public static FieldValue newFloat(int fieldNumber, float value) {
        return new FieldValue(fieldNumber, Type.FLOAT, value);
    }

    public static FieldValue newDouble(int fieldNumber, double value) {
        return new FieldValue(fieldNumber, Type.DOUBLE, value);
    }

    public static FieldValue newString(int fieldNumber, String value) {
        return new FieldValue(fieldNumber, Type.STRING, value);
    }

    public static FieldValue newDateTime(int fieldNumber, Date value) {
        return new FieldValue(fieldNumber, Type.DATETIME, value);
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldValue that = (FieldValue) o;
        return fieldNumber == that.fieldNumber &&
            type == that.type &&
            value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldNumber, type, value);
    }
}
