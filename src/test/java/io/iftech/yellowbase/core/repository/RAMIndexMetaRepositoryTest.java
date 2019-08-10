package io.iftech.yellowbase.core.repository;

import static org.junit.Assert.*;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.IndexMeta;
import io.iftech.yellowbase.core.SegmentMeta;
import org.junit.Test;

public class RAMIndexMetaRepositoryTest {

    @Test
    public void createAndGet() {

        IndexMetaRepository repository = new RAMIndexMetaRepository();

        repository.createSegmentMeta(SegmentMeta.newInstance());
        repository.createSegmentMeta(SegmentMeta.newInstance());
        repository.createSegmentMeta(SegmentMeta.newInstance());
        IndexMeta meta = repository.getIndexMeta();
        Truth.assertThat(meta.getSegmentMetas()).hasSize(3);
        Truth.assertThat(meta.getSegmentMetas().get(0).maxDocs()).isEqualTo(0);
        Truth.assertThat(meta.getSegmentMetas().get(1).maxDocs()).isEqualTo(0);
        Truth.assertThat(meta.getSegmentMetas().get(2).maxDocs()).isEqualTo(0);
    }
}