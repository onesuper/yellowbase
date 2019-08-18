package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;

public enum Type {
    STRING(0),
    BIGINT(1),
    INT(2),
    DOUBLE(3),
    FLOAT(4),
    DATETIME(5);

    private int code;

    Type(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    static Map<Integer, Type> typeByCode = new HashMap<>();
    static {
        for (Type t : Type.values()) {
            typeByCode.put(t.code, t);
        }
    }
    public static Type parseFromCode(int code) {
        Preconditions.checkState(typeByCode.containsKey(code),
            "Type code unmatched: " + code);
        return typeByCode.get(code);
    }
}
