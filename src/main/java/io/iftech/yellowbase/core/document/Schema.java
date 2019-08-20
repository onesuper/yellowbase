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

    Schema(List<FieldEntry> fields) {
        this.fields = fields;
        this.fieldsByName = fields.stream().collect(
            Collectors.toMap(FieldEntry::getName, this::makeField));
    }

    private Field makeField(FieldEntry fieldEntry) {
        return new Field(fieldEntry.getName(), fieldEntry.getFieldNumber());
    }

    public Optional<Field> getField(String name) {
        return Optional.ofNullable(this.fieldsByName.get(name));
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

    static class SchemaBuilder {

        private List<FieldEntry> fields = new LinkedList<>();

        private SchemaBuilder() {
        }

        public SchemaBuilder addStringField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.STRING, options));
            return this;
        }

        public SchemaBuilder addFloatField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.FLOAT, options));
            return this;
        }

        public SchemaBuilder addDoubleField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.DOUBLE, options));
            return this;
        }

        public SchemaBuilder addDatetimeField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.DATETIME, options));
            return this;
        }

        public SchemaBuilder addIntField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.INT, options));
            return this;
        }

        public SchemaBuilder addBigIntField(String name, int fieldNumber, Options options) {
            this.fields.add(new FieldEntry(name, fieldNumber, Type.BIGINT, options));
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
