package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.SegmentReader;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.query.BooleanClause.Kind;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
        Map<Kind, Collection<Weight>> weightsByKinds = new HashMap<>();

        clauseByKind.forEach((kind, subClauses) -> {

            Consumer<Query> weighter = clause ->
                weightsByKinds.computeIfAbsent(kind, k -> new ArrayList<>())
                    .add(clause.weight());

            subClauses.forEach(weighter);
        });

        return new BooleanWeight(weightsByKinds);
    }

    private class BooleanWeight implements Weight {

        private Map<Kind, Collection<Weight>> subWeightsByKind;

        public BooleanWeight(Map<Kind, Collection<Weight>> subWeightsByKind) {
            this.subWeightsByKind = subWeightsByKind;
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
