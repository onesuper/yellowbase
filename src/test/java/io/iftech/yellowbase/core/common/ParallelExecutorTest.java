package io.iftech.yellowbase.core.common;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.Test;

public class ParallelExecutorTest {

    @Test
    public void executorCanMapValues() {

        Executor executor = new ParallelExecutor(
            Executors.newSingleThreadExecutor());
        List<String> result = executor.map((i) -> i + "", ImmutableList.of(1, 2, 3));
        Truth.assertThat(result).containsExactly("1", "2", "3");
    }
}