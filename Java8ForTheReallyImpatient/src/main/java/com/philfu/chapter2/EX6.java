package com.philfu.chapter2;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX6 implements Exercise {
    @Override
    public void perform() {

    }

    public static Stream<Character> characterStream(String s) {
        return IntStream.rangeClosed(0, s.length() - 1).mapToObj(s::charAt);
    }
}
