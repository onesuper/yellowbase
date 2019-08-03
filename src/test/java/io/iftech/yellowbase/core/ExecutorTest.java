package io.iftech.yellowbase.core;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Test;

public class ExecutorTest {

    @Test
    public void executorCanMapValues() {

        Executor executor = new Executor();
        List<String> result = executor.map((i) -> i + "", ImmutableList.of(1, 2, 3));
        Truth.assertThat(result).containsExactly("1", "2", "3");
    }
}