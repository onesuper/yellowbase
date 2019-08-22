package io.iftech.yellowbase.core.document;

public class Field {

    public final String name;

    public final int fieldNumber;

    // Don't generate outside the package !!
    Field(String name, int fieldNumber) {
        this.name = name;
        this.fieldNumber = fieldNumber;
    }
}
