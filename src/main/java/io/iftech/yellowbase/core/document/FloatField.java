package io.iftech.yellowbase.core.document;

public final class FloatField extends Field {

    public FloatField(String name, float value) {
        super(name);
        this.data = value;
    }
}
