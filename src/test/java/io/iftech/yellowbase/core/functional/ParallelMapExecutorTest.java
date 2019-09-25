package io.iftech.yellowbase.core.functional;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import io.iftech.yellowbase.core.common.MapExecutor;
import io.iftech.yellowbase.core.common.ParallelMapExecutor;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.Test;

public class ParallelMapExecutorTest {

    @Test
    public void executorCanMapValues() {

        MapExecutor<Integer, String> mapExecutor = new ParallelMapExecutor<>(
            Executors.newSingleThreadExecutor());
        List<String> result = mapExecutor.map((i) -> i + "", ImmutableList.of(1, 2, 3));
        Truth.assertThat(result).containsExactly("1", "2", "3");
    }
}