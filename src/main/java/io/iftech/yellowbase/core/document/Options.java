package io.iftech.yellowbase.core.document;

import com.google.gson.annotations.Expose;
import java.util.Objects;

public final class Options {

    public static Options DEFAULT = new Options(true, true);

    public static Options STORED = new Options(true, false);

    public static Options INDEXED = new Options(false, true);

    @Expose
    private boolean stored;
    @Expose
    private boolean indexed;

    private Options(boolean stored, boolean indexed) {
        this.stored = stored;
        this.indexed = indexed;
    }

    public boolean isStored() {
        return stored;
    }

    public boolean isIndexed() {
        return indexed;
    }

    @Override
    public String toString() {
        return "Options{" +
            "stored=" + stored +
            ", indexed=" + indexed +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Options options = (Options) o;
        return stored == options.stored &&
            indexed == options.indexed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stored, indexed);
    }
}
