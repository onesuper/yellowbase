package io.iftech.yellowbase.core.index;

import io.iftech.yellowbase.core.SegmentMeta;
import io.iftech.yellowbase.core.common.RwLockGuard;
import java.util.ArrayList;
import java.util.List;

public final class SegmentManager {

    private static class SegmentRegistries {
        private SegmentRegistry uncommitted;
        private SegmentRegistry committed;
    }

    private final RwLockGuard<SegmentRegistries> rwLockGuard;

    private SegmentManager(SegmentRegistries registries) {
        this.rwLockGuard = new RwLockGuard<>(registries);
    }

    public static SegmentManager fromSegments(List<SegmentMeta> segmentMetas) {
        SegmentRegistries registries = new SegmentRegistries();
        registries.committed = SegmentRegistry.empty();
        registries.uncommitted = SegmentRegistry.fromSegments(segmentMetas);
        return new SegmentManager(registries);
    }

    public void addSegment(SegmentEntry entry) {
        rwLockGuard.write(r -> r.uncommitted.add(entry));
    }

    public void commit(List<SegmentEntry> entries) {
        rwLockGuard.write(r -> {
            r.committed.clear();
            r.uncommitted.clear();
            for (SegmentEntry entry : entries) {
                r.committed.add(entry);
            }
        });
    }

    public List<SegmentEntry> getAllSegmentEntries() {
        final List<SegmentEntry> entries = new ArrayList<>();
        rwLockGuard.read(r -> {
            entries.addAll(r.committed.getAllSegmentEntries());
            entries.addAll(r.uncommitted.getAllSegmentEntries());
        });

        return entries;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();

        rwLockGuard.read(
            r -> sb.append("SegmentManager{")
                .append(", uncommitted=")
                .append(r.uncommitted)
                .append(", committed=")
                .append(r.committed)
                .append('}')
        );

        return sb.toString();
    }
}
