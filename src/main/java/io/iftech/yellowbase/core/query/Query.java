package io.iftech.yellowbase.core.query;

public abstract class Query {
    /**
     * 使用访问者模式，解耦访问逻辑和处理逻辑
     *
     * @param visitor
     */
    public abstract void accept(QueryVisitor visitor);
}
