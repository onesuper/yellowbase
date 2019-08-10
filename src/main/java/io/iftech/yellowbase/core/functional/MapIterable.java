package io.iftech.yellowbase.core.functional;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface MapIterable {

    <I, R> List<R> map(Function<I, R> f, Iterable<I> is);

}
