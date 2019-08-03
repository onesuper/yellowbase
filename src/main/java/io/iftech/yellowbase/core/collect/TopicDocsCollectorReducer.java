package io.iftech.yellowbase.core.collect;

import java.util.Collection;

public final class TopicDocsCollectorReducer implements CollectorReducer<TopDocsCollector, TopDocs> {
    @Override
    public TopDocsCollector newCollector() {
        return new SimpleTopDocsCollector();
    }

    @Override
    public TopDocs reduce(Collection<TopDocsCollector> collectors) {
        final TopDocs[] topDocs = new TopDocs[collectors.size()];
        int i = 0;
        for (TopDocsCollector collector : collectors) {
            topDocs[i++] = collector.topDocs();
        }
        return TopDocs.merge(topDocs);
    }
}
