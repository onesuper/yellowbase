package io.iftech.yellowbase.core.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class DirectorExecutor implements MapIterable {
    @Override
    public <I, R> List<R> map(Function<I, R> f, Iterable<I> is) {
        List<R> result = new ArrayList<>();
        for (I i : is) {
            result.add(f.apply(i));
        }
        return result;
    }
}
