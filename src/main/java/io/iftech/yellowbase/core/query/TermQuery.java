package io.iftech.yellowbase.core.query;

import io.iftech.yellowbase.core.index.Term;
import java.util.Objects;

public class TermQuery extends Query {

    private final Term term;

    public TermQuery(Term t) {
        term = Objects.requireNonNull(t);
    }

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.consumeTerms(this, term);
    }

    public Term getTerm() {
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TermQuery termQuery = (TermQuery) o;
        return Objects.equals(term, termQuery.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }
}
