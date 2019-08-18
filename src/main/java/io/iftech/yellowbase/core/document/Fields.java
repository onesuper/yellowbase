package io.iftech.yellowbase.core.document;

import java.util.Date;

public final class Fields {
    public static Field as(String name, String s) {
        return new StringField(name, s);
    }

    public static Field as(String name, int i) {
        return new IntegerField(name, i);
    }

    public static Field as(String name, long l) {
        return new BigIntField(name, l);
    }

    public static Field as(String name, float f) {
        return new FloatField(name, f);
    }

    public static Field as(String name, double d) {
        return new DoubleField(name, d);
    }

    public static Field as(String name, Date d) {
        return new DateTimeField(name, d);
    }

    // Classes for defining fields

    public static final class StringField extends Field {
        public StringField(String name, String s) {
            super(name, Type.STRING, s);
        }
    }

    public static final class IntegerField extends Field {
        public IntegerField(String name, int i) {
            super(name, Type.INT, i);
        }
    }

    public static final class BigIntField extends Field {
        public BigIntField(String name, long l) {
            super(name, Type.BIGINT, l);
        }
    }

    public static final class FloatField extends Field {
        public FloatField(String name, float f) {
            super(name, Type.FLOAT, f);
        }
    }

    public static final class DoubleField extends Field {
        public DoubleField(String name, double d) {
            super(name, Type.DOUBLE, d);
        }
    }

    public static final class DateTimeField extends Field {
        public DateTimeField(String name, Date d) {
            super(name, Type.DATETIME, d);
        }
    }
}
