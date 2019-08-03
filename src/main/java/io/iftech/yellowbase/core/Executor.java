package io.iftech.yellowbase.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

public class Executor {

    private final ExecutorService executor;

    public Executor() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public Executor(ExecutorService executor) {
        this.executor = executor;
    }

    public <I, R> List<R> map(Function<I, R> f, Iterable<I> is) {
        final List<Future<R>> resultFutures = new LinkedList<>();
        for (I i : is) {
            FutureTask<R> task = new FutureTask<>(() -> f.apply(i));
            resultFutures.add(task);
            executor.execute(task);
        }

        final List<R> result = new LinkedList<>();
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
