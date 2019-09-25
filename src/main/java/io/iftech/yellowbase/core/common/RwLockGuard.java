package io.iftech.yellowbase.core.common;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class RwLockGuard<T> implements RwLockReadGuard<T>, RwLockWriteGuard<T>{

    private final Lock readLock;
    private final Lock writeLock;
    private T guarded;

    public RwLockGuard(T guarded) {
        this(new ReentrantReadWriteLock());
        this.guarded = guarded;
    }

    private RwLockGuard(ReadWriteLock lock) {
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @Override
    public void read(Consumer<T> callback) {
        readLock.lock();
        try {
            callback.accept(guarded);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void write(Consumer<T> callback) {
        writeLock.lock();
        try {
            callback.accept(guarded);
        } finally {
            writeLock.unlock();
        }
    }
}