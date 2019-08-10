package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.docset.DocSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Union<DocId extends Comparable<DocId>> implements DocSet<DocId> {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private List<DocSet<DocId>> docSets;
    private Iterator<DocId> bufferedIterator;
    private NavigableSet<DocId> buffer;
    private int bufferSize;
    private DocId current;

    @SafeVarargs
    public static <DocId extends Comparable<DocId>> Union<DocId> of(
        DocSet<DocId>... docSets) {
        return of(Arrays.asList(docSets));
    }

    public static <DocId extends Comparable<DocId>> Union<DocId> of(
        Collection<DocSet<DocId>> docSets) {
        return new Union<>(docSets, DEFAULT_BUFFER_SIZE);
    }

    private Union(Collection<DocSet<DocId>> docSets, int bufferSize) {
        this.docSets = new LinkedList<>(docSets);
        this.bufferSize = bufferSize;
    }

    /**
     * 使用有一个有序集合缓冲区
     *
     * @return false 当全部文档已经遍历完毕
     */
    @Override
    public boolean next() {

        if (advanceBuffer()) {
            return true;
        }

        if (refillBuffer()) {
            return next();
        }

        return false;
    }

    /*
     * 在缓冲区用完时，用多个 docSets 重新填满缓冲区
     *
     * 当 docSets 都为空时，且缓冲区也为空时，返回 false
     */
    private boolean refillBuffer() {

        buffer = new TreeSet<>();

        List<DocSet<DocId>> nonEmpty = new LinkedList<>();

        for (DocSet<DocId> docSet : docSets) {
            boolean isEmpty = tryRefill(docSet);
            if (!isEmpty) {
                nonEmpty.add(docSet);
            }
        }

        docSets = nonEmpty;
        bufferedIterator = this.buffer.iterator();
        return docSets.size() > 0 || bufferedIterator.hasNext();
    }

    /*
     * 用某个给定 docSetIterator 装满 buffer
     *
     * 当 docSetIterator 为空时，返回 true
     *
     * 若填满则返回 false
     */
    private boolean tryRefill(DocSet<DocId> docSet) {
        while (docSet.next()) {
            if (buffer.size() > bufferSize) {
                return false;
            }
            buffer.add(docSet.docId());
        }

        return true;
    }

    /*
     * 缓冲的游向前移动一个位置，当缓冲完后返回 false
     */
    private boolean advanceBuffer() {
        if (this.bufferedIterator != null && this.bufferedIterator.hasNext()) {
            this.current = bufferedIterator.next();
            return true;
        }
        return false;
    }

    @Override
    public DocId docId() {
        return this.current;
    }

    @Override
    public long size() {
        return 0;
    }
}
