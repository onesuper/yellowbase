package io.iftech.yellowbase.core.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Document implements Iterable<Field> {

    private final List<Field> fields = new ArrayList<>();

    @NotNull
    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    public Document add(Field field) {
        fields.add(field);
        return this;
    }
}
