package io.iftech.yellowbase.core.common;

import java.util.function.Consumer;

public interface RwLockReadGuard<T> {

    void read(Consumer<T> callback);
}
