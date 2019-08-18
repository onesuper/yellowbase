package io.iftech.yellowbase.core.io;

import java.io.IOException;

public interface BinaryDeserializable<T> {

    T deserialize() throws IOException;
}
