package io.iftech.yellowbase.core.collect;

import java.util.Collection;

public interface TopDocsCollector extends Collector {

    TopDocs topDocs();

    /**
     * 使用 TopDocsCollector 进行收集，得到一个 TopDocs
     *
     * @param collectors
     * @return
     */
    static TopDocs reduce(Collection<TopDocsCollector> collectors) {
        final TopDocs[] topDocs = new TopDocs[collectors.size()];
        int i = 0;
        for (TopDocsCollector collector : collectors) {
            topDocs[i++] = collector.topDocs();
        }
        return TopDocs.merge(topDocs);
    }
}
