package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;
import java.util.Objects;

/**
 * FieldEntry 中记录了字段的类型和索引信息
 */
public class FieldEntry {

    @Expose
    private final String name;
    @Expose
    private final int fieldNumber;
    @Expose
    private final Type type;
    @Expose
    private final Options options;

    FieldEntry(String name, int fieldNumber, Type type, Options options) {
        this.name = Preconditions.checkNotNull(name);
        this.fieldNumber = fieldNumber;
        this.type = Preconditions.checkNotNull(type);
        this.options = Preconditions.checkNotNull(options);
    }

    public String getName() {
        return name;
    }

    public int getFieldNumber() {
        return fieldNumber;
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
            "name='" + name + '\'' +
            ", fieldNumber=" + fieldNumber +
            ", type=" + type +
            ", options=" + options +
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
        FieldEntry that = (FieldEntry) o;
        return fieldNumber == that.fieldNumber &&
            name.equals(that.name) &&
            type == that.type &&
            options.equals(that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fieldNumber, type, options);
    }
}
