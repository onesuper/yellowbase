package io.iftech.yellowbase.core.document;

public class FieldEntry {

    private final Field field;
    private final Type type;
    private final Option option;

    FieldEntry(Field field, Type type, Option option) {
        this.field = field;
        this.type = type;
        this.option = option;
    }

    FieldEntry(String name, Type type, Option option) {
        this.field = new Field(name);
        this.type = type;
        this.option = option;
    }

    @Override
    public String toString() {
        return "FieldEntry{" +
            "field=" + field +
            ", type=" + type +
            ", option=" + option +
            '}';
    }
}
