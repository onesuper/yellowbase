package io.iftech.yellowbase.core.index;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.iftech.yellowbase.core.Index;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SegmentUpdater {

    private Index index; // back-ref

    private ExecutorService executorService;

    private SegmentManager segmentManager;

    private AtomicInteger generation;

    public SegmentUpdater(Index index) {
        this.index = index;
        this.executorService = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder().setNameFormat("yellowbase-segment-updater").build());
        this.segmentManager =  SegmentManager.fromSegments(index.getSegmentMetas());
    }

    public void commit() {
        runSync(() -> {
            this.segmentManager.commit(segmentManager.getAllSegmentEntries());
        });
    }

    private void saveMetas() {

    }

    public void addSegment(SegmentEntry entry) {
        runSync(()->this.segmentManager.addSegment(entry));
    }

    // Utils

    private void runAsync(Runnable task) {
        executorService.submit(task);
    }

    private void runSync(Runnable task) {
        try {
            executorService.submit(task).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
