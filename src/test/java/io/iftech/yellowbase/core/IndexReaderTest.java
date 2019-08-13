package io.iftech.yellowbase.core;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.repository.IndexMetaRepository;
import io.iftech.yellowbase.core.repository.RAMIndexMetaRepository;
import java.util.stream.Collectors;
import org.junit.Test;

public class IndexReaderTest {

    @Test
    public void reflectOnSearcherAfterReload() {
        IndexMetaRepository repository = new RAMIndexMetaRepository();

        repository.createSegmentMeta(new SegmentMeta("s1", 1));
        repository.createSegmentMeta(new SegmentMeta("s2", 1));

        Index index = new Index(repository);
        IndexReader reader = index.reader();
        reader.reload();

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2");

        repository.createSegmentMeta(new SegmentMeta("s3", 1));

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2");

        reader.reload();

        Truth.assertThat(reader.searcher().getSegmentReaders().stream()
            .map(SegmentReader::id).collect(Collectors.toList()))
            .containsExactly("s1", "s2", "s3");
    }
}