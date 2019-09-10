package io.iftech.yellowbase.core.query;

public abstract class Query {

    public abstract void accept(QueryVisitor visitor);

    /**
     * QueryNode implements this to calculate weight for query evaluation
     */
    public abstract Weight weight();
}
