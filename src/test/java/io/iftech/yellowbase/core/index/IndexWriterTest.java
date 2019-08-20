package io.iftech.yellowbase.core.index;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.Index;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.Options;
import io.iftech.yellowbase.core.document.Schema;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;

public class IndexWriterTest {

    @Test
    public void indexAsynchronously() throws Exception {

        Index index = Index.createInRAM();
        IndexWriter writer = index.writer(4);

        CountDownLatch latch = new CountDownLatch(5000);
        writer.setPostIndexHandler(doc -> latch.countDown());

        Schema schema = Schema.builder()
            .addStringField("a", 1, Options.DEFAULT)
            .build();

        for (int i = 0; i < 5000; i++) {
            writer.addDocument(new Document().addString(schema.getField("a"), "test"));
        }

        latch.await();
        Truth.assertThat(true).isTrue();
    }

    @Test
    public void indexedImmediatelyAfterCommit() throws Exception {

        Index index = Index.createInRAM();
        IndexWriter writer = index.writer(4);

        CountDownLatch latch = new CountDownLatch(1000);
        writer.setPostIndexHandler(doc -> latch.countDown());

        Schema schema = Schema.builder()
            .addStringField("b", 1, Options.DEFAULT)
            .build();

        for (int i = 0; i < 1000; i++) {
            writer.addDocument(new Document().addString(schema.getField("b"), "test"));
        }

        writer.commit();
        Truth.assertThat(latch.getCount()).isEqualTo(0);
    }

    @Test
    public void asyncIndexAfterOneSuccessfulCommit() throws Exception {

        Index index = Index.createInRAM();
        IndexWriter writer = index.writer(4);

        CountDownLatch latch1 = new CountDownLatch(1000);
        writer.setPostIndexHandler(doc -> latch1.countDown());

        Schema schema = Schema.builder()
            .addStringField("a", 1, Options.DEFAULT)
            .addStringField("b", 2, Options.DEFAULT)
            .build();

        for (int i = 0; i < 1000; i++) {
            writer.addDocument(new Document().addString(schema.getField("a"), "test"));
        }

        writer.commit();
        Truth.assertThat(latch1.getCount()).isEqualTo(0);

        CountDownLatch latch2 = new CountDownLatch(100);
        writer.setPostIndexHandler(doc -> latch2.countDown());

        for (int i = 0; i < 100; i++) {
            writer.addDocument(new Document().addString(schema.getField("b"), "test"));
        }

        latch2.await();
        Truth.assertThat(true).isTrue();
    }
}