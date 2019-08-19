package io.iftech.yellowbase.core.document;

public final class Option {
    private boolean stored;
    private boolean indexed;

    public Option(boolean stored, boolean indexed) {
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
        return "Option{" +
            "stored=" + stored +
            ", indexed=" + indexed +
            '}';
    }
}
