package io.iftech.yellowbase.core.index;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IndexWorkers {

    private final ExecutorService executor;

    private List<CompletableFuture<Void>> futureHandles;

    public IndexWorkers(ExecutorService executor) {
        this.executor = executor;
    }

    public void spawn(Runnable runnable, int numWorkers) {
        futureHandles = IntStream.range(0, numWorkers).boxed()
            .map(i -> CompletableFuture.runAsync(runnable, executor))
            .collect(Collectors.toList());
    }

    public void join() throws InterruptedException, ExecutionException {
        for (CompletableFuture<Void> future: futureHandles) {
            future.get();
        }
    }
}
