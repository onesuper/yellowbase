package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import java.util.Date;
import java.util.Objects;

public class FieldValue {

    private int fieldNumber;
    private FieldType fieldType;
    private Object value;

    FieldValue(int fieldNumber, FieldType fieldType, Object value) {
        this.fieldNumber = fieldNumber;
        this.fieldType = Preconditions.checkNotNull(fieldType);
        this.value = Preconditions.checkNotNull(value);
    }

    public static FieldValue newInt(int fieldNumber, int value) {
        return new FieldValue(fieldNumber, FieldType.INT, value);
    }

    public static FieldValue newBigInt(int fieldNumber, long value) {
        return new FieldValue(fieldNumber, FieldType.BIGINT, value);
    }

    public static FieldValue newFloat(int fieldNumber, float value) {
        return new FieldValue(fieldNumber, FieldType.FLOAT, value);
    }

    public static FieldValue newDouble(int fieldNumber, double value) {
        return new FieldValue(fieldNumber, FieldType.DOUBLE, value);
    }

    public static FieldValue newString(int fieldNumber, String value) {
        return new FieldValue(fieldNumber, FieldType.STRING, value);
    }

    public static FieldValue newDateTime(int fieldNumber, Date value) {
        return new FieldValue(fieldNumber, FieldType.DATETIME, value);
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "FieldValue{" +
            "fieldNumber=" + fieldNumber +
            ", fieldType=" + fieldType +
            ", value=" + value +
            '}';
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
            fieldType == that.fieldType &&
            value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldNumber, fieldType, value);
    }
}
