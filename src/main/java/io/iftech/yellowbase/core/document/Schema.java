package io.iftech.yellowbase.core.document;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Schema {

    private List<FieldEntry> fields;

    private Map<String, FieldEntry> fieldsByName;

    public Schema(List<FieldEntry> fields) {
        this.fields = fields;
        this.fieldsByName = fields.stream().collect(
            Collectors.toMap(e -> e.getField().getName(), Function.identity()));
    }

    public Optional<FieldEntry> getFieldEntry(String name) {
        return Optional.ofNullable(this.fieldsByName.get(name));
    }

    public static SchemaBuilder builder() {
        return new SchemaBuilder();
    }

    static class SchemaBuilder {

        private List<FieldEntry> fields = new LinkedList<>();

        private SchemaBuilder() {
        }

        public SchemaBuilder addStringField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.STRING, option));
            return this;
        }

        public SchemaBuilder addFloatField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.FLOAT, option));
            return this;
        }

        public SchemaBuilder addDoubleField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.DOUBLE, option));
            return this;
        }

        public SchemaBuilder addDatetimeField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.DATETIME, option));
            return this;
        }

        public SchemaBuilder addIntField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.INT, option));
            return this;
        }

        public SchemaBuilder addBigIntField(String name, Option option) {
            this.fields.add(new FieldEntry(name, Type.BIGINT, option));
            return this;
        }

        public Schema build() {
            return new Schema(fields);
        }
    }
}
