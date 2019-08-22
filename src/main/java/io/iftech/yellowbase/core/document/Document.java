package io.iftech.yellowbase.core.document;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Document {

    private final List<FieldValue> fields = new ArrayList<>();

    private final Schema schema; // back-ref

    public Document(Schema schema) {
        this.schema = schema;
    }

    public List<FieldValue> getFields() {
        return ImmutableList.copyOf(fields);
    }

    public Schema getSchema() {
        return this.schema;
    }

    // named document

    public List<FieldValue> getSortedFieldValues() {
        List<FieldValue> ret = new ArrayList<>(this.fields);
        ret.sort(Comparator.comparingInt(f -> f.fieldNumber));
        return ret;
    }

    /**
     * Convert the document to named values according to {@link Schema}
     *
     * Used in {@link #toJson(Schema)} method
     *
     * A field will be ignored if its number can not found in {@link Schema}
     */
    public Map<String, Object> toNamedValues(Schema schema) {
        Map<String, Object> ret = new LinkedHashMap<>();
        for (FieldValue fieldValue : getSortedFieldValues()) {
            schema.getFieldByNumber(fieldValue.fieldNumber)
                .ifPresent(value -> ret.put(value.name, fieldValue.value));
        }
        return ret;
    }

    public String toJson(Schema schema) {
        Gson gson = new Gson();
        return gson.toJson(toNamedValues(schema));
    }

    // field composer

    public Document addString(String name, String s) {
        this.fields.add(FieldValue.newString(schema.getField(name).fieldNumber, s));
        return this;
    }

    public Document addFloat(String name, float f) {
        this.fields.add(FieldValue.newFloat(schema.getField(name).fieldNumber, f));
        return this;
    }

    public Document addDouble(String name, double d) {
        this.fields.add(FieldValue.newDouble(schema.getField(name).fieldNumber, d));
        return this;
    }

    public Document addInt(String name, int i) {
        this.fields.add(FieldValue.newInt(schema.getField(name).fieldNumber, i));
        return this;
    }

    public Document addBigInt(String name, long l) {
        this.fields.add(FieldValue.newBigInt(schema.getField(name).fieldNumber, l));
        return this;
    }

    public Document addDateTime(String name, Date d) {
        this.fields.add(FieldValue.newDateTime(schema.getField(name).fieldNumber, d));
        return this;
    }

    public Document add(FieldValue field) {
        this.fields.add(field);
        return this;
    }

    @Override
    public String toString() {
        return "Document{" +
            "fields=" + fields +
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
        Document document = (Document) o;
        return fields.equals(document.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
