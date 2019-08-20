package io.iftech.yellowbase.core.document;

/**
 * FieldEntry 中记录了字段的类型和索引信息
 */
public class FieldEntry {

    private final Field field;
    private final Type type;
    private final Options options;

    FieldEntry(Field field, Type type, Options options) {
        this.field = field;
        this.type = type;
        this.options = options;
    }

    FieldEntry(String name, Type type, Options options) {
        this.field = new Field(name);
        this.type = type;
        this.options = options;
    }

    public Field getField() {
        return field;
    }

    public Type getType() {
        return type;
    }

    public Options getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "FieldEntry{" +
            "field=" + field +
            ", type=" + type +
            ", options=" + options +
            '}';
    }
}
