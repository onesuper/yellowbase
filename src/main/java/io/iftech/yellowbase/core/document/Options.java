package io.iftech.yellowbase.core.document;

public final class Options {

    public static Options DEFAULT = new Options(true, true);

    public static Options STORED = new Options(true, false);

    public static Options INDEXED = new Options(false, true);


    private boolean stored;
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
}
