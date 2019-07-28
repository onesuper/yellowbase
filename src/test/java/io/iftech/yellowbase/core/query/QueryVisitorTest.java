package io.iftech.yellowbase.core.query;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.index.Term;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class QueryVisitorTest {

    private static final Query query = BooleanQueries.or(
        BooleanQueries.and(
            new TermQuery(new Term("fieldA", "a")),
            new TermQuery(new Term("fieldB", "b"))),
        new TermQuery(new Term("fieldC", "c")));

    @Test
    public void testExtractTermsEqual() {
        Set<Term> terms = new HashSet<>();
        QueryVisitor visitor = new QueryVisitor() {
            @Override
            public void consumeTerms(Query query, Term... ts) {
                terms.addAll(Arrays.asList(ts));
            }

            @Override
            public QueryVisitor getSubVisitor(Query parent) {
                return this;
            }
        };

        query.accept(visitor);
        Truth.assertThat(terms).containsExactly(
            new Term("fieldA", "a"),
            new Term("fieldB", "b"),
            new Term("fieldC", "c")
        );
    }
}