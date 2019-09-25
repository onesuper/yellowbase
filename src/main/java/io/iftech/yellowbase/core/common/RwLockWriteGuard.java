package io.iftech.yellowbase.core.common;

import java.util.function.Consumer;

public interface RwLockWriteGuard<T> {

    void write(Consumer<T> block);

}
