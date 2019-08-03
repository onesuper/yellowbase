package io.iftech.yellowbase.core.search;

import io.iftech.yellowbase.core.document.Document;

public interface Indexable<DocId> {

    void add(Document document, DocId docId);
}
