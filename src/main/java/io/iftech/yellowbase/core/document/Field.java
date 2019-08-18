package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import java.util.Objects;

public class Field {

    private final String name;
    private final Type type;
    private Object value;

    Field(String name, Type type, Object value) {
        this.name = Preconditions.checkNotNull(name);
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        return name.equals(field.name) &&
            type == field.type &&
            value.equals(field.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value);
    }

    @Override
    public String toString() {
        return "Field{" +
            "name='" + name + '\'' +
            ", type=" + type +
            '}';
    }
}
