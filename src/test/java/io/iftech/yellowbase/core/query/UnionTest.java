package io.iftech.yellowbase.core.query;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.docset.ListDocSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class UnionTest {

    @Test
    public void unionTwoSetsWithIntersection() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 2, 3));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(3, 6));

        DocSet<Integer> union = Union.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (union.next()) {
            result.add(union.docId());
        }
        Truth.assertThat(result).containsExactly(1, 2, 3, 6);
    }

    @Test
    public void unionMultipleSetsWithNoIntersection() {

        DocSet<Integer> first = new ListDocSet<>(ImmutableList.of(1, 2, 3));
        DocSet<Integer> second = new ListDocSet<>(ImmutableList.of(4, 5, 6));
        DocSet<Integer> third = new ListDocSet<>(ImmutableList.of(7, 8, 9));

        DocSet<Integer> union = Union.of(first, second, third);

        List<Integer> result = new LinkedList<>();
        while (union.next()) {
            result.add(union.docId());
        }
        Truth.assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void unionWithEmptySet() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 2, 3));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.<Integer>of());

        DocSet<Integer> union = Union.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (union.next()) {
            result.add(union.docId());
        }
        Truth.assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    public void unionTwoEmptySets() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.<Integer>of());
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.<Integer>of());

        DocSet<Integer> union = Union.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (union.next()) {
            result.add(union.docId());
        }
        Truth.assertThat(result).isEmpty();
    }

}