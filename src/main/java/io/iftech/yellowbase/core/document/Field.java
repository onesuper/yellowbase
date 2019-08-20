package io.iftech.yellowbase.core.document;

public class Field {

    private final String name;

    private final int fieldNumber;

    public Field(String name, int fieldNumber) {
        this.name = name;
        this.fieldNumber = fieldNumber;
    }

    public String getName() {
        return name;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }
}
