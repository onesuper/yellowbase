package io.iftech.yellowbase.core.query;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.docset.ListDocSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class IntersectionTest {

    @Test
    public void intersectTwoSets1() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(3, 6));
        DocSet<Integer> intersection = Intersection.of(left, right);
        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).containsExactly(3, 6);
    }

    @Test
    public void intersectTwoSets2() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(3, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> intersection = Intersection.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).containsExactly(3, 6);
    }

    @Test
    public void intersectionMultipleSets() {
        DocSet<Integer> first = new ListDocSet<>(ImmutableList.of(2, 3, 6));
        DocSet<Integer> second = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> third = new ListDocSet<>(ImmutableList.of(2, 4, 6, 8));

        DocSet<Integer> intersection = Intersection.of(first, second, third);

        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).containsExactly(2, 6);
    }

    @Test
    public void intersectTwoSetsWithNoIntersection() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 3, 5, 7, 9));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(2, 4, 6, 8, 10));
        DocSet<Integer> intersection = Intersection.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).isEmpty();
    }

    @Test
    public void intersectWithEmptySet1() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.<Integer>of());
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(2, 4, 6));
        DocSet<Integer> intersection = Intersection.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).isEmpty();
    }

    @Test
    public void intersectWithEmptySet2() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(2, 4, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.<Integer>of());
        DocSet<Integer> intersection = Intersection.of(left, right);

        List<Integer> result = new LinkedList<>();
        while (intersection.next()) {
            result.add(intersection.docId());
        }
        Truth.assertThat(result).isEmpty();
    }

    @Test
    public void operationIsNotFunctional() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(3, 6));
        DocSet<Integer> intersection1 = Intersection.of(left, right);

        List<Integer> result1 = new LinkedList<>();
        while (intersection1.next()) {
            result1.add(intersection1.docId());
        }
        Truth.assertThat(result1).containsExactly(3, 6);

        DocSet<Integer> intersection2 = Intersection.of(left, right);
        List<Integer> result2 = new LinkedList<>();
        while (intersection2.next()) {
            result2.add(intersection2.docId());
        }
        Truth.assertThat(result2).isEmpty();
    }
}