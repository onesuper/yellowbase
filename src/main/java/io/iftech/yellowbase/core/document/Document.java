package io.iftech.yellowbase.core.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Document {

    private final List<FieldValue> fields = new ArrayList<>();

    public List<FieldValue> getFields() {
        return fields;
    }

    public Document add(FieldValue field) {
        this.fields.add(field);
        return this;
    }

    public Document add(Field field, String s) {
        this.fields.add(new FieldValue(field, Type.STRING, s));
        return this;
    }

    public Document add(Field field, int i) {
        this.fields.add(new FieldValue(field, Type.INT, i));
        return this;
    }

    public Document add(Field field, long l) {
        this.fields.add(new FieldValue(field, Type.BIGINT, l));
        return this;
    }

    public Document add(Field field, float f) {
        this.fields.add(new FieldValue(field, Type.FLOAT, f));
        return this;
    }

    public Document add(Field field, double d) {
        this.fields.add(new FieldValue(field, Type.DOUBLE, d));
        return this;
    }

    public Document add(Field field, Date d) {
        this.fields.add(new FieldValue(field, Type.DATETIME, d));
        return this;
    }
}
