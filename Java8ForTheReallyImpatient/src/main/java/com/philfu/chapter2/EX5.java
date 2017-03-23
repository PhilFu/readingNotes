package com.philfu.chapter2;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX5 implements Exercise {
    @Override
    @Test
    public void perform() {

    }

    private LongStream lcg(long a, int c, long m, long seed) {
        return LongStream.iterate(seed, xn -> (a * xn + c)%m);
    }
}
