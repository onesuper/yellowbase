package io.iftech.yellowbase.core.docset;

import com.google.common.truth.Truth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class ListDocSetTest {

    @Test
    public void testListDocSet() {
        List<Integer> numbers = IntStream.range(0, 10).map(x -> x * 3).boxed()
            .collect(Collectors.toList());
        DocSet<Integer> docSet = new ListDocSet<>(numbers);

        Truth.assertThat(docSet.next()).isTrue();

        Truth.assertThat(docSet.next()).isTrue();
        Truth.assertThat(docSet.docId()).isEqualTo(3);
        Truth.assertThat(docSet.next()).isTrue();
        Truth.assertThat(docSet.docId()).isEqualTo(6);
        Truth.assertThat(docSet.next()).isTrue();
        Truth.assertThat(docSet.docId()).isEqualTo(9);

        Truth.assertThat(docSet.advance(24)).isTrue();
        Truth.assertThat(docSet.docId()).isEqualTo(24);

        Truth.assertThat(docSet.advance(25)).isTrue();
        Truth.assertThat(docSet.docId()).isEqualTo(27);

        Truth.assertThat(docSet.advance(44)).isFalse();
    }
}