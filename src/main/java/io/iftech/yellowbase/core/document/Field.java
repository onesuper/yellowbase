package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;

public class Field {

    private final String name;

    protected Object data;

    Field(String name) {
        this.name = Preconditions.checkNotNull(name);

    }

    public String getName() {
        return name;
    }

    public String getAsString() {
        return (String) data;
    }


}
