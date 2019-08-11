package io.iftech.yellowbase.core.functional;

import java.util.function.Consumer;

@FunctionalInterface
public interface RwLockReadGuard<T> {

    void read(Consumer<T> callback);
}
