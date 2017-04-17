package com.philfu.chapter2;

import java.util.List;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.counting;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/14.
 */
public class EX13 implements Exercise {
    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();
        words.parallelStream()
                .filter(w -> w.length()<12)
                .collect(groupingBy(String::length, counting()))
                .forEach((k, v) -> System.out.printf("%d - %d\n", k, v));
    }
}
