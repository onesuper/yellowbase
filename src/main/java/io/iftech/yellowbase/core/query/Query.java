package io.iftech.yellowbase.core.query;

public abstract class Query {
    /**
     * 使用访问者模式，解耦访问逻辑和处理逻辑
     *
     * @param visitor
     */
    public abstract void accept(QueryVisitor visitor);

    /**
     * 原子 query 实现这个方法，计算权重
     */
    public abstract Weight weight();
}
