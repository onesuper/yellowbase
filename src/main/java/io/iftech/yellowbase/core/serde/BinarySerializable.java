package io.iftech.yellowbase.core.serde;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BinarySerializable<T> {
    T deserialize(InputStream inputStream) throws IOException;

    void serialize(T t, OutputStream outputStream) throws IOException;
}
