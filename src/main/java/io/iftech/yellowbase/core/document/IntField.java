package io.iftech.yellowbase.core.document;

public final class IntField extends Field {

    public IntField(String name, int value) {
        super(name);
        this.data = value;
    }
}
