package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBen on 2016/5/28.
 */
public class EX8 implements Exercise{

    @Override
    @Test
    public void perform() {
        String[] names = { "Peter", "Paul", "Mary" };
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add( () -> System.out.println(name));
        }

        for (Runnable runner : runners) {
            new Thread(runner).start();
        }
    }
}
