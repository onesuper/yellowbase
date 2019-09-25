package io.iftech.yellowbase.core.functional;

import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.common.RwLockGuard;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class RwLockGuardTest {

    @Test
    public void read() throws Exception {

        RwLockGuard<Set<Integer>> rwLockGuard = new RwLockGuard<>(new HashSet<>());

        Thread writer1 = new Thread(() -> {
            for (int i = 0; i <= 1000; i += 2) {

                final Integer even = i;
                rwLockGuard.write(x -> x.add(even));
            }
        });

        Thread writer2 = new Thread(() -> {
            for (int i = 0; i <= 1000; i += 1) {
                final Integer odd = i;
                rwLockGuard.write(x -> x.add(odd));
            }
        });

        writer1.start();
        writer2.start();

        writer1.join();
        writer2.join();

        rwLockGuard.read(x -> Truth.assertThat(x).containsAllIn(
            IntStream.range(0, 1000).boxed().collect(Collectors.toList())));
    }
}