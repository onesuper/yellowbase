package io.iftech.yellowbase.core.query.combiner;

import io.iftech.yellowbase.core.query.Scorer;

public interface ScoreCombiner {

    /**
     * 将一个 {@link Scorer} 聚合到 ScoreCombiner 中
     *
     * 调用者自己决定是否需要 调用 {@link #score()}
     */
    void combine(Scorer scorer);

    /**
     * @return 聚合以后的得分
     */
    float score();
}
