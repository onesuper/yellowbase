package io.iftech.yellowbase.core;

import java.util.ArrayList;
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

    public <A, R> List<R> map(Function<A, R> f, Iterable<A> as) {
        final List<Future<R>> resultFutures = new ArrayList<>();
        for (A a : as) {
            FutureTask<R> task = new FutureTask<>(() -> f.apply(a));
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
