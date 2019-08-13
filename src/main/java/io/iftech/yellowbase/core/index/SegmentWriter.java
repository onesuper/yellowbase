package io.iftech.yellowbase.core.index;

import io.iftech.yellowbase.core.Segment;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.repository.SegmentRepository;

public class SegmentWriter {

    private int maxDocs;

    private Segment segment;

    private SegmentRepository segmentRepository;

    public SegmentWriter(Segment segment, SegmentRepository segmentRepository) {
        this.segment = segment;
        this.segmentRepository = segmentRepository;
    }

    public void addDocument(Document document) {
        this.segmentRepository.addDocument(document);
        this.maxDocs += 1;
    }

    public int maxDocs() {
        return maxDocs;
    }

    /**
     * @return 当前 segment writer 往外写出的字节大小，帮助判断是否需要开一个新的 segment
     */
    public int memoryUsage() {

        return -1;
    }
}
