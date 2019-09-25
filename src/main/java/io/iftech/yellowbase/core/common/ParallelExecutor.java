package io.iftech.yellowbase.core.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

public final class ParallelExecutor implements Executor {

    private final ExecutorService executor;

    public ParallelExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public <I, R> List<R> map(Function<I, R> f, Iterable<I> is) {

        final List<Future<R>> resultFutures = new LinkedList<>();
        for (I i : is) {
            FutureTask<R> task = new FutureTask<>(() -> f.apply(i));
            resultFutures.add(task);
            executor.execute(task);
        }

        final List<R> result = new ArrayList<>();
        for (Future<R> future : resultFutures) {
            try {
                result.add(future.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}