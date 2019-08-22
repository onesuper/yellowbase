package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;
import java.util.Objects;

/**
 * FieldEntry records the type and index information of all fields in schema
 */
public class FieldEntry {

    @Expose
    public final String name;
    @Expose
    public final int fieldNumber;
    @Expose
    public final FieldType fieldType;
    @Expose
    public final Options options;

    FieldEntry(String name, int fieldNumber, FieldType fieldType, Options options) {
        this.name = Preconditions.checkNotNull(name);
        this.fieldNumber = fieldNumber;
        this.fieldType = Preconditions.checkNotNull(fieldType);
        this.options = Preconditions.checkNotNull(options);
    }

    @Override
    public String toString() {
        return "FieldEntry{" +
            "name='" + name + '\'' +
            ", fieldNumber=" + fieldNumber +
            ", fieldType=" + fieldType +
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
            fieldType == that.fieldType &&
            options.equals(that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fieldNumber, fieldType, options);
    }
}
