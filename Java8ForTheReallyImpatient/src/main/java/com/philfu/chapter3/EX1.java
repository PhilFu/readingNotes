package com.philfu.chapter3;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/19.
 */
public class EX1 implements Exercise{
    @Override
    @Test
    public void perform() {
        Logger.getGlobal().setLevel(Level.OFF);
        logIf(Level.INFO, () -> true, () -> "you'll never see it");

        Logger.getGlobal().setLevel(Level.ALL);
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : a) {
            logIf(Level.INFO, () -> i == 10, () -> "a[10] = " + a[10]);
        }
    }

    public void logIf(Level level, Supplier<Boolean> condition, Supplier<String> message) {
        Logger logger = Logger.getGlobal();
        if (logger.isLoggable(level) && condition.get()) {
            logger.log(level, message.get());
        }
    }
}
