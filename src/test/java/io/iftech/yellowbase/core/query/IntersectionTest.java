package io.iftech.yellowbase.core.query;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.docset.DocSet;
import io.iftech.yellowbase.core.docset.ListDocSet;
import org.junit.Test;

public class IntersectionTest {

    @Test
    public void bigIntersectSmall() {

        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(3, 6));
        DocSet<Integer> intersection = new Intersection<>(left, right).docSet();
        Truth.assertThat(intersection.next()).isTrue();

        Truth.assertThat(intersection.docId()).isEqualTo(3);
        Truth.assertThat(intersection.next()).isTrue();
        Truth.assertThat(intersection.docId()).isEqualTo(6);
        Truth.assertThat(intersection.next()).isFalse();
    }

    @Test
    public void smallIntersectBig() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(3, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(1, 2, 3, 4, 5, 6));
        DocSet<Integer> intersection = new Intersection<>(left, right).docSet();
        Truth.assertThat(intersection.next()).isTrue();

        Truth.assertThat(intersection.docId()).isEqualTo(3);
        Truth.assertThat(intersection.next()).isTrue();
        Truth.assertThat(intersection.docId()).isEqualTo(6);
        Truth.assertThat(intersection.next()).isFalse();
    }

    @Test
    public void noIntersection() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(1, 3, 5, 7, 9));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(2, 4, 6, 8, 10));
        DocSet<Integer> intersection = new Intersection<>(left, right).docSet();
        Truth.assertThat(intersection.next()).isFalse();
        Truth.assertThat(intersection.docId()).isNull();
    }

    @Test
    public void emptyIntersectBeing() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.<Integer>of());
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.of(2, 4, 6));
        DocSet<Integer> intersection = new Intersection<>(left, right).docSet();
        Truth.assertThat(intersection.next()).isFalse();
    }

    @Test
    public void beingIntersectEmpty() {
        DocSet<Integer> left = new ListDocSet<>(ImmutableList.of(2, 4, 6));
        DocSet<Integer> right = new ListDocSet<>(ImmutableList.<Integer>of());
        DocSet<Integer> intersection = new Intersection<>(left, right).docSet();
        Truth.assertThat(intersection.next()).isFalse();
    }
}