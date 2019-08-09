package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.docset.DocSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class Intersection<DocId extends Comparable<DocId>> implements DocSet<DocId> {

    private final DocSet<DocId> left;
    private final DocSet<DocId> right;

    private Intersection(DocSet<DocId> left, DocSet<DocId> right) {
        this.left = left;
        this.right = right;
    }

    @SafeVarargs
    public static <DocId extends Comparable<DocId>> Intersection<DocId> of(
        DocSet<DocId>... docSets) {
        return of(Arrays.asList(docSets));
    }

    public static <DocId extends Comparable<DocId>> Intersection<DocId> of(
        Collection<DocSet<DocId>> docSets) {
        Queue<DocSet<DocId>> queue = new LinkedList<>(docSets);
        DocSet<DocId> head = queue.poll();

        if (queue.size() == 1) {
            return new Intersection<>(head, queue.poll());
        }

        return new Intersection<>(head, of(queue));
    }

    /**
     * left 和 right 文档集合通过在互相在对方中查询自己当前的文档来实现集合求交的过程
     *
     * 使用 advance 方法可以提高在集合中查询指定文档的效率
     *
     * @return 当 left 或 right 中任何一个为空时，返回 false
     */
    @Override
    public boolean next() {

        if (!left.next()) {
            return false;
        }

        while (true) {

            // 在 right 中找 left 的当前元素
            DocId target = left.docId();

            if (!right.advance(target)) {
                return false;
            }

            if (right.docId().equals(target)) {
                return true;
            }

            // 在 left 中找 right 的当前元素
            target = right.docId();
            if (!left.advance(target)) {
                return false;
            }

            if (left.docId().equals(target)) {
                return true;
            }
        }
    }

    @Override
    public DocId docId() {
        return left.docId();
    }

    @Override
    public long size() {
        return left.size();
    }
}
