package io.iftech.yellowbase.core.search;

import io.iftech.yellowbase.core.query.Query;

public interface Searchable {
     SearchResult search(Query query, int maxResults);
}
