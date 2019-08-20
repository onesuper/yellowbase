package io.iftech.yellowbase.core.document;

public final class Fields {

    // Classes for defining fields

    public static final class StringField extends FieldEntry {

        public StringField(String name, String s, Options o) {
            super(name, Type.STRING, o);
        }
    }

    public static final class IntegerField extends FieldEntry {

        public IntegerField(String name, Options o) {
            super(name, Type.INT, o);
        }
    }

    public static final class BigIntField extends FieldEntry {

        public BigIntField(String name, Options o) {
            super(name, Type.BIGINT, o);
        }
    }

    public static final class FloatField extends FieldEntry {

        public FloatField(String name, Options o) {
            super(name, Type.FLOAT, o);
        }
    }

    public static final class DoubleField extends FieldEntry {

        public DoubleField(String name, Options o) {
            super(name, Type.DOUBLE, o);
        }
    }

    public static final class DateTimeField extends FieldEntry {

        public DateTimeField(String name, Options o) {
            super(name, Type.DATETIME, o);
        }
    }
}
