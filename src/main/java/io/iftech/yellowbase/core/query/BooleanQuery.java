package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.SegmentReader;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.query.BooleanClause.Kind;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * BooleanQuery 由多个 BooleanClause 节点组成
 *
 * 每个 BooleanClause 节点可以是任意的 Query（例如 TermQuery）
 *
 * 或是一个 BooleanQuery
 */
public class BooleanQuery extends Query {

    final Map<Kind, Collection<Query>> clauseByKind;

    BooleanQuery() {
        this.clauseByKind = new EnumMap<>(Kind.class);
    }

    @Override
    public void accept(QueryVisitor visitor) {
        for (BooleanClause.Kind kind : clauseByKind.keySet()) {
            if (clauseByKind.get(kind).size() > 0) {
                QueryVisitor v = visitor.getSubVisitor(this);
                for (Query q : clauseByKind.get(kind)) {
                    q.accept(v);
                }
            }
        }
    }

    @Override
    public Weight weight() {
        return new BooleanWeight(clauseByKind);
    }

    private class BooleanWeight implements Weight {
        private Map<Kind, Collection<Query>> clauseByKind;

        public BooleanWeight(Map<Kind, Collection<Query>> clauseByKind) {
            this.clauseByKind = clauseByKind;
        }

        @Override
        public Scorer scorer() {
            return new ConstantScorer(1);
        }

        @Override
        public DocSet<Integer> docSetIterator(SegmentReader segmentReader) {
            return null;
        }
    }
}
