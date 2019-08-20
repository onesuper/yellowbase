package io.iftech.yellowbase.core.io.proto;

import com.google.common.truth.Truth;
import org.junit.Test;

public class ProtobufSkipReaderWriterTest {

    @Test
    public void testSkip() throws Exception {
        ProtobufSkipWriter writer = new ProtobufSkipWriter();

        writer.writeDoc(1);
        writer.writeDoc(5);
        writer.writeDoc(9);

        byte[] data = writer.getBytes();

        ProtobufSkipReader reader = new ProtobufSkipReader(data);

        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(1);
        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(5);
        Truth.assertThat(reader.next()).isTrue();
        Truth.assertThat(reader.docId()).isEqualTo(9);
        Truth.assertThat(reader.next()).isFalse();
    }

}