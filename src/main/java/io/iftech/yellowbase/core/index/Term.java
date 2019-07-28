package io.iftech.yellowbase.core.index;

import org.jetbrains.annotations.NotNull;

public final class Term implements Comparable<Term> {

    private final String field;

    private final String value;

    public Term(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull Term other) {
        if (field.equals(other.field)) {
            return value.compareTo(other.value);
        } else {
            return field.compareTo(other.field);
        }
    }

    @Override
    public String toString() {
        return field + ":" + value;
    }
}
