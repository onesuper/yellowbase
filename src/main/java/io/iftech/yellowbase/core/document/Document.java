package io.iftech.yellowbase.core.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Document {

    private final List<FieldValue> fields = new ArrayList<>();

    public List<FieldValue> getFields() {
        return fields;
    }

    public Document addString(Field field, String s) {
        this.fields.add(FieldValue.newString(field.getFieldNumber(), s));
        return this;
    }

    public Document addFloat(Field field, float f) {
        this.fields.add(FieldValue.newFloat(field.getFieldNumber(), f));
        return this;
    }

    public Document addDouble(Field field, float d) {
        this.fields.add(FieldValue.newDouble(field.getFieldNumber(), d));
        return this;
    }

    public Document addInt(Field field, int i) {
        this.fields.add(FieldValue.newInt(field.getFieldNumber(), i));
        return this;
    }

    public Document addBigInt(Field field, long l) {
        this.fields.add(FieldValue.newBigInt(field.getFieldNumber(), l));
        return this;
    }

    public Document addDateTime(Field field, Date d) {
        this.fields.add(FieldValue.newDateTime(field.getFieldNumber(), d));
        return this;
    }

    public Document add(FieldValue field) {
        this.fields.add(field);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return fields.equals(document.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
