package io.iftech.yellowbase.core.document;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;

public enum FieldType {
    STRING(0),
    BIGINT(1),
    INT(2),
    DOUBLE(3),
    FLOAT(4),
    DATETIME(5);

    private int code;

    FieldType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    static Map<Integer, FieldType> typeByCode = new HashMap<>();
    static {
        for (FieldType t : FieldType.values()) {
            typeByCode.put(t.code, t);
        }
    }
    public static FieldType parseFromCode(int code) {
        Preconditions.checkState(typeByCode.containsKey(code),
            "FieldType code unmatched: " + code);
        return typeByCode.get(code);
    }
}
