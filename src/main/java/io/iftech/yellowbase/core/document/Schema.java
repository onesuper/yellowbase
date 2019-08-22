package io.iftech.yellowbase.core.document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Schema {

    @Expose
    private List<FieldEntry> fields;

    // For fast retrieve field through schema
    private Map<String, Field> fieldsByName;

    private Map<Integer, Field> fieldsByNumber;

    Schema(List<FieldEntry> fields) {
        this.fields = fields;
        this.fieldsByName = fields.stream().collect(
            Collectors.toMap(f -> f.name, this::makeField));

        this.fieldsByNumber = fields.stream().collect(
            Collectors.toMap(f -> f.fieldNumber, this::makeField));
    }

    private Field makeField(FieldEntry fieldEntry) {
        return new Field(fieldEntry.name, fieldEntry.fieldNumber);
    }

    public Optional<Field> getOptField(String name) {
        return Optional.ofNullable(this.fieldsByName.get(name));
    }

    public Field getField(String name) {
        return getOptField(name).orElseThrow(() -> new IllegalArgumentException("Can not find field: " + name));
    }

    public Optional<Field> getFieldByNumber(int fieldNumber) {
        return Optional.ofNullable(this.fieldsByNumber.get(fieldNumber));
    }

    // helpers

    public String toJson(boolean pretty) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();

        if (pretty) {
            builder.setPrettyPrinting();
        }

        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public static Schema fromJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, Schema.class);
    }

    public static SchemaBuilder builder() {
        return new SchemaBuilder();
    }

    public static class SchemaBuilder {

        private List<FieldEntry> fields = new LinkedList<>();

        private SchemaBuilder() {
        }

        public SchemaBuilder addStringField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.STRING, options));
            return this;
        }

        public SchemaBuilder addFloatField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.FLOAT, options));
            return this;
        }

        public SchemaBuilder addDoubleField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.DOUBLE, options));
            return this;
        }

        public SchemaBuilder addDatetimeField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.DATETIME, options));
            return this;
        }

        public SchemaBuilder addIntField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.INT, options));
            return this;
        }

        public SchemaBuilder addBigIntField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, FieldType.BIGINT, options));
            return this;
        }

        public Schema build() {
            return new Schema(fields);
        }
    }

    @Override
    public String toString() {
        return "Schema{" +
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
        Schema schema = (Schema) o;
        return fields.equals(schema.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
