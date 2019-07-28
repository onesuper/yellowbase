package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.index.Term;

public abstract class QueryVisitor {

    public abstract void consumeTerms(Query query, Term... terms);

    public abstract QueryVisitor getSubVisitor(Query parent);

    public static final QueryVisitor EMPTY_VISITOR = new QueryVisitor() {

        @Override
        public void consumeTerms(Query query, Term... terms) {
        }

        @Override
        public QueryVisitor getSubVisitor(Query parent) {
            return this;
        }
    };
}
