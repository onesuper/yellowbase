package io.iftech.yellowbase.core.io;

import java.io.IOException;

public interface BinarySerializable<T> {

    void serialize(T t) throws IOException;

}
