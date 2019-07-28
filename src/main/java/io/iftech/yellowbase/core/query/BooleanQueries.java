package io.iftech.yellowbase.core.query;

import com.google.common.collect.ImmutableSet;
import io.iftech.yellowbase.core.query.BooleanClause.Kind;

public class BooleanQueries {

    public static BooleanQuery and(Query... queries) {
        return new AndQuery(queries);
    }

    public static BooleanQuery or(Query... queries) {
        return new OrQuery(queries);
    }

    public static BooleanQuery not(Query query) {
        return new NotQuery(query);
    }

    public static final class AndQuery extends BooleanQuery {

        AndQuery(Query... queries) {
            super();
            clauseByKind.put(Kind.AND, ImmutableSet.copyOf(queries));
        }
    }

    public static final class OrQuery extends BooleanQuery {

        OrQuery(Query... queries) {
            super();
            clauseByKind.put(Kind.OR, ImmutableSet.copyOf(queries));
        }
    }

    public static final class NotQuery extends BooleanQuery {

        NotQuery(Query query) {
            super();
            clauseByKind.put(Kind.NOT, ImmutableSet.of(query));
        }
    }
}
