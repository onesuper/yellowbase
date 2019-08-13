package io.iftech.yellowbase.core;

import com.google.common.truth.Truth;
import java.util.stream.Collectors;
import org.junit.Test;

public class IndexReaderTest {

    @Test
    public void reflectOnSearcherAfterReload() {

        Index index = Index.createInRAM();
        index.getIndexMetaRepository().createSegmentMeta(new SegmentMeta("s1", 1));
        index.getIndexMetaRepository().createSegmentMeta(new SegmentMeta("s2", 1));


        IndexReader reader = index.reader();
        reader.reload();

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2");

        index.getIndexMetaRepository().createSegmentMeta(new SegmentMeta("s3", 1));

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2");

        reader.reload();

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2", "s3");
    }
}