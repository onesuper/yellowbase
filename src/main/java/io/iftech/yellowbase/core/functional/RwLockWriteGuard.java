package io.iftech.yellowbase.core.functional;

import java.util.function.Consumer;

@FunctionalInterface
public interface RwLockWriteGuard<T> {

    void write(Consumer<T> block);

}
