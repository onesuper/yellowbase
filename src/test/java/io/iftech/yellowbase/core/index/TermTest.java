package io.iftech.yellowbase.core.index;


import com.google.common.truth.Truth;
import org.junit.Test;

public class TermTest {

    @Test
    public void termsAreSortedLexically() {
        Truth.assertThat(new Term("name", "John"))
            .isLessThan(new Term("name", "Mary"));

        Truth.assertThat(new Term("name", "John"))
            .isEqualTo(new Term("name", "John"));

        Truth.assertThat(new Term("name", "John"))
            .isLessThan(new Term("sex", "Male"));

        Truth.assertThat(new Term("name", "Zoe"))
            .isLessThan(new Term("sex", "Male"));
    }
}