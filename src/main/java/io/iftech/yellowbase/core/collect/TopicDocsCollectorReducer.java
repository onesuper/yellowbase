package io.iftech.yellowbase.core.collect;

import java.util.Collection;

public final class TopicDocsCollectorReducer<DocId>
    implements CollectorReducer<TopDocsCollector<DocId>, TopDocs> {
    @Override
    public TopDocsCollector<DocId> newCollector() {
        return new SimpleTopDocsCollector<>();
    }

    @Override
    public TopDocs reduce(Collection<TopDocsCollector<DocId>> collectors) {
        final TopDocs[] topDocs = new TopDocs[collectors.size()];
        int i = 0;
        for (TopDocsCollector collector : collectors) {
            topDocs[i++] = collector.topDocs();
        }
        return TopDocs.merge(topDocs);
    }
}
