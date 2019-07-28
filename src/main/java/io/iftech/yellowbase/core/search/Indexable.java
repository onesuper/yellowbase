package io.iftech.yellowbase.core.search;

import io.iftech.yellowbase.core.document.Document;

public interface Indexable {

    void add(Document document, String docId);
}
