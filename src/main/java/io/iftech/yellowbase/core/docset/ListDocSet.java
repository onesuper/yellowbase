package io.iftech.yellowbase.core.docset;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class ListDocSet<DocId extends Comparable<DocId>> implements DocSet<DocId> {

    private List<DocId> docIds;
    private int cursor;

    public ListDocSet(List<DocId> docIds) {
        this.docIds = ImmutableList.copyOf(docIds);
        this.cursor = -1;
    }

    @Override
    public boolean next() {
        cursor += 1;
        return docIds.size() > cursor;
    }

    @Override
    public DocId docId() {
        if (cursor >= docIds.size()) {
            return null;
        }
        return docIds.get(cursor);
    }

    @Override
    public long size() {
        return docIds.size();
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < docIds.size(); i++) {
            if (i == cursor) {
                sb.append("[");
            }
            sb.append(docIds.get(i));
            if (i == cursor) {
                sb.append("]");
            }
            sb.append(" ");
        }

        return sb.toString();
    }
}
