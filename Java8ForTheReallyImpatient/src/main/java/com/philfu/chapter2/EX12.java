package com.philfu.chapter2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/14.
 */
public class EX12 implements Exercise {
    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();
        AtomicInteger[] shortWords = new AtomicInteger[12];
        words.parallelStream().forEach(w -> {
            int length = w.length();
            if (length < 12) {
                AtomicInteger i = shortWords[length];
                if (i == null) {
                    i = new AtomicInteger();
                    shortWords[length] = i;
                }
                i.getAndIncrement();
            }
        });
        Arrays.asList(shortWords).forEach(System.out::println);
    }
}
