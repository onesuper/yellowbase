package io.iftech.yellowbase.core.docset;


import java.util.function.Consumer;

public interface DocSet<DocId extends Comparable<DocId>> {

    /**
     * 移动到下一个有效的文档
     *
     * 必须先调用一次 next()，才能得到第一个有效的 docId：
     *
     * while(docSetIterator.next()) {
     *   process(docSetIterator.docId)
     * }
     *
     * @return false 如果没有文档了
     */
    boolean next();

    /**
     * 直接跳到某一个文档后的一个文档
     *
     * 默认实现通过反复调用 {@link #next()} 方法来实现
     *
     * 跳表可以通过覆盖该方法实现高性能的查询
     *
     * @return false 如果没有文档了
     */
    default boolean advance(DocId target) {

        if (!this.next()) {
            return false;
        }

        while (true) {
            int val = this.docId().compareTo(target);

            if (val < 0) {
                if (!this.next()) {
                    return false;
                }

            } else {
                return true;
            }
        }
    }

    default void foreach(Consumer<DocId> callback) {
        while (next()) {
            callback.accept(docId());
        }
    }

    /**
     * 获取当前文档的 DocId
     */
    DocId docId();

    /**
     * 获取当前文档集合的大小
     */
    long size();
}
