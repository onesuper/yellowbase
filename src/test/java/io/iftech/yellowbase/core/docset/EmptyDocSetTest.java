package io.iftech.yellowbase.core.docset;

import com.google.common.truth.Truth;
import org.junit.Test;

public class EmptyDocSetTest {

    @Test
    public void testEmptyDocSet() {
        DocSet<Integer> docSet = new EmptyDocSet();
        Truth.assertThat(docSet.size()).isEqualTo(0);

        Truth.assertThat(docSet.next()).isFalse();
    }
}