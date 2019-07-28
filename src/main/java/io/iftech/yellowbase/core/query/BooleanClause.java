package io.iftech.yellowbase.core.query;

import com.google.common.base.Objects;

public class BooleanClause {

    public enum Kind {
        AND,
        OR,
        NOT
    }

    private final Query query;
    private final Kind kind;

    public BooleanClause(Query query, Kind kind) {
        this.query = query;
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BooleanClause that = (BooleanClause) o;
        return Objects.equal(query, that.query) &&
            kind == that.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(query, kind);
    }

    public Query getQuery() {
        return query;
    }

    public Kind getKind() {
        return kind;
    }
}
