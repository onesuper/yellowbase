package io.iftech.yellowbase.core.query;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.index.Term;
import io.iftech.yellowbase.core.query.BooleanClause.Kind;
import org.junit.Test;

public class BooleanQueryTest {

    @Test
    public void testAndQueryBuildEqual() {

        Query q1 = new TermQuery(new Term("fieldA", "a"));
        Query q2 = new TermQuery(new Term("fieldB", "b"));

        BooleanQuery bq = BooleanQueries.and(q1, q2);
        Truth.assertThat(bq.clauseByKind.get(Kind.AND)).containsExactly(q1, q2);
    }

    @Test
    public void testOrQueryCanBuildEqual() {

        Query q1 = new TermQuery(new Term("fieldA", "a"));
        Query q2 = new TermQuery(new Term("fieldB", "b"));

        BooleanQuery bq = BooleanQueries.or(q1, q2);
        Truth.assertThat(bq.clauseByKind.get(Kind.OR)).containsExactly(q1, q2);
    }

    @Test
    public void testNotQueryCanBuildEqual() {

        Query q1 = new TermQuery(new Term("fieldA", "a"));

        BooleanQuery bq = BooleanQueries.not(q1);
        Truth.assertThat(bq.clauseByKind.get(Kind.NOT)).containsExactly(q1);
    }


    @Test
    public void testComplexBooleanQueryCanBuildEqual() {

        Query q1 = new TermQuery(new Term("fieldA", "a"));
        Query q2 = new TermQuery(new Term("fieldB", "b"));
        Query q3 = new TermQuery(new Term("'fieldC", "c"));

        BooleanQuery bq = BooleanQueries.or(q3, BooleanQueries.and(q1, q2));
        Truth.assertThat(bq.clauseByKind.get(Kind.OR)).hasSize(2);
    }
}