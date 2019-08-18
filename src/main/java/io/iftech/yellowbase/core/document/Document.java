package io.iftech.yellowbase.core.document;

import java.util.ArrayList;
import java.util.List;

public class Document{

    private final List<Field> fields = new ArrayList<>();

    public List<Field> getFields() {
        return fields;
    }

    public Document add(Field field) {
        this.fields.add(field);
        return this;
    }
}
