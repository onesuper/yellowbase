package io.iftech.yellowbase.core.collect;

public interface TopDocsCollector<DocId> extends Collector<DocId> {
    TopDocs topDocs();
}
