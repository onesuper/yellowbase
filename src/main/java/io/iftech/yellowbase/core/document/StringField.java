package io.iftech.yellowbase.core.document;

public final class StringField extends Field {

    public StringField(String name, String value) {
        super(name);
        this.data = value;
    }
}
