package io.iftech.yellowbase.core.common;

import java.util.List;
import java.util.function.Function;

public interface Executor {

    <I, R> List<R> map(Function<I, R> f, Iterable<I> is);

}
