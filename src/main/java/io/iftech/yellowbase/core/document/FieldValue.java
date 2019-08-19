package io.iftech.yellowbase.core.document;

import java.util.Date;

public class FieldValue {

    private Field field;
    private Type type;
    private Object value;

    public FieldValue(Field field, Type type, Object value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public static FieldValue as(String name, String s) {
        return new FieldValue(new Field(name), Type.STRING, s);
    }

    public static FieldValue as(String name, int i) {
        return new FieldValue(new Field(name), Type.INT, i);
    }

    public static FieldValue as(String name, long l) {
        return new FieldValue(new Field(name), Type.BIGINT, l);
    }

    public static FieldValue as(String name, float f) {
        return new FieldValue(new Field(name), Type.FLOAT, f);
    }

    public static FieldValue as(String name, double d) {
        return new FieldValue(new Field(name), Type.DOUBLE, d);
    }

    public static FieldValue as(String name, Date d) {
        return new FieldValue(new Field(name), Type.DATETIME, d);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
