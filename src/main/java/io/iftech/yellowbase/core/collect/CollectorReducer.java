package io.iftech.yellowbase.core.collect;

import java.util.Collection;

public interface CollectorReducer<C extends Collector, T> {

    /**
     * 用来为每个 Mapper 创建 Collector
     */
    C newCollector();

    T reduce(Collection<C> collectors);
}
