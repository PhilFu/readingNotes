package com.philfu.chapter2;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX4 implements Exercise {
    @Override
    @Test
    public void perform() {
        int[] values = {1, 4, 9, 16};
        Stream<int[]> a = Stream.of(values);
        IntStream b = Arrays.stream(values);
    }
}
