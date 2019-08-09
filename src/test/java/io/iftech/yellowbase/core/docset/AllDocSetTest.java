package io.iftech.yellowbase.core.docset;

import com.google.common.truth.Truth;
import org.junit.Test;

public class AllDocSetTest {

    @Test
    public void testAllDocSet() {
        DocSet<Integer> docSet = new AllDocSet(3);

        Truth.assertThat(docSet.size()).isEqualTo(3);
        int i = 0;

        while (docSet.next()) {
            Truth.assertThat(docSet.docId()).isEqualTo(i);
            i++;
        }

        Truth.assertThat(i).isEqualTo(3);
    }
}