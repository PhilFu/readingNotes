package com.philfu.chapter2;

import java.util.List;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX2 implements Exercise{
    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();
        words.stream().filter(x -> x.length()>12).limit(5).forEach(System.out::println);
    }
}
