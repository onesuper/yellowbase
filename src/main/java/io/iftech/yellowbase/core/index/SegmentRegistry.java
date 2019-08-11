package io.iftech.yellowbase.core.index;

import com.google.common.collect.ImmutableList;
import io.iftech.yellowbase.core.SegmentMeta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SegmentRegistry {

    private Map<String, SegmentEntry> segmentEntryByIds;

    public SegmentRegistry(List<SegmentMeta> segmentMetas) {
        this.segmentEntryByIds = new HashMap<>();
        for (SegmentMeta meta : segmentMetas) {
            this.segmentEntryByIds.put(meta.id(), new SegmentEntry(meta));
        }
    }

    public void add(SegmentEntry entry) {
        segmentEntryByIds.put(entry.segmentId(), entry);
    }

    public Optional<SegmentEntry> get(String segmentId) {
        return Optional.ofNullable(segmentEntryByIds.get(segmentId));
    }

    public void clear() {
        segmentEntryByIds.clear();
    }

    public void remove(String segmentId) {
        segmentEntryByIds.remove(segmentId);
    }
    public List<SegmentEntry> getAllSegmentEntries() {
        return ImmutableList.copyOf(segmentEntryByIds.values());
    }
}
