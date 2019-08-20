package io.iftech.yellowbase.core.document;

public final class Option {

    public static Option DEFAULT = new Option(true, true);

    public static Option STORED = new Option(true, false);

    public static Option INDEXED = new Option(false, true);


    private boolean stored;
    private boolean indexed;

    private Option(boolean stored, boolean indexed) {
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
