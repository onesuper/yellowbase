package io.iftech.yellowbase.core.index;

import io.iftech.yellowbase.core.Index;
import io.iftech.yellowbase.core.document.Document;
import io.iftech.yellowbase.core.document.StringField;
import org.junit.Test;

public class IndexWriterTest {

    @Test
    public void test() throws Exception {

        Index index = Index.createInRAM();

        IndexWriter writer = index.writer(4);

        for (int i = 0; i < 1000; i++) {
            writer.addDocument(new Document().add(new StringField("a", "zz")));
        }

        writer.commit();

        for (int i = 0; i < 1000; i++) {
            writer.addDocument(new Document().add(new StringField("b", "bb")));
        }

        writer.commit();
        Thread.sleep(1000);


    }
}