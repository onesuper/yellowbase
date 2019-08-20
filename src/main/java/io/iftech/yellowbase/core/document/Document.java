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

    public List<FieldValue> getFields() {
        return ImmutableList.copyOf(fields);
    }

    // named document

    public List<FieldValue> getSortedFieldValues() {
        List<FieldValue> ret = new ArrayList<>(this.fields);
        ret.sort(Comparator.comparingInt(FieldValue::getFieldNumber));
        return ret;
    }

    /**
     * 根据 {@link Schema} 转换为名字到值的映射，在 {@link #toJson(Schema)} 方法中使用
     *
     * 当 {@link FieldValue#getFieldNumber()} 无法在 {@link Schema} 找到时，会主动忽略
     */
    public Map<String, Object> toNamedFieldDocument(Schema schema) {
        Map<String, Object> ret = new LinkedHashMap<>();
        for (FieldValue fieldValue : getSortedFieldValues()) {
            schema.getFieldByNumber(fieldValue.getFieldNumber())
                .ifPresent(value -> ret.put(value.getName(), fieldValue.getValue()));
        }
        return ret;
    }

    public String toJson(Schema schema) {
        Gson gson = new Gson();
        return gson.toJson(toNamedFieldDocument(schema));
    }

    // field composer

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
