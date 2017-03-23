package com.philfu.chapter2;

import java.util.List;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX3 implements Exercise {
    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();
        long time1 = countWithStream(words);
        long time2 = countWithParllelStream(words);

        System.out.println("With normal Stream: " + time1 + " ns");
        System.out.println("With parell Stream: " + time2 + " ns");
    }

    private long countWithStream(List<String> words) {
        long start = System.nanoTime();
        long wordCount = words.stream().filter(x -> x.length() > 12).count();
        long end = System.nanoTime();
        return end - start;
    }

    private long countWithParllelStream(List<String> words) {
        long start = System.nanoTime();
        long wordCount = words.parallelStream().filter(x -> x.length() > 12).count();
        long end = System.nanoTime();
        return end - start;
    }
}
