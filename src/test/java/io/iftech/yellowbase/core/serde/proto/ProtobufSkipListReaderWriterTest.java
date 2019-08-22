package io.iftech.yellowbase.core.serde.proto;

import com.google.common.truth.Truth;
import org.junit.Test;

public class ProtobufSkipListReaderWriterTest {

    @Test
    public void testSkip() throws Exception {
        ProtobufSkipListWriter writer = new ProtobufSkipListWriter();

        writer.writeDoc(1);
        writer.writeDoc(5);
        writer.writeDoc(9);

        byte[] data = writer.getBytes();

        ProtobufSkipListReader reader = new ProtobufSkipListReader(data);

        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(1);
        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(5);
        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(9);
        Truth.assertThat(reader.next()).isFalse();
    }

}