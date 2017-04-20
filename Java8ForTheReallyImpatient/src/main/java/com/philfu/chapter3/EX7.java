package com.philfu.chapter3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;

import static com.philfu.chapter3.EX7.CompareOptions.SPACE_INSENSITIVE;
import static com.philfu.chapter3.EX7.CompareOptions.CASE_INSENSITIVE;
import static com.philfu.chapter3.EX7.CompareOptions.REVERSE;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/20.
 */
public class EX7 implements Exercise {
    @Override
    @Test
    public void perform() {
        String[] values = {
                "BBB",
                " ccc",
                "aaa"
        };
        Arrays.sort(values, comparatorGenerator(EnumSet.of(
                REVERSE,
                CASE_INSENSITIVE,
                SPACE_INSENSITIVE
        )));
        assertArrayEquals(values, new String[]{
                " ccc",
                "BBB",
                "aaa"
        });
        Arrays.sort(values, comparatorGenerator(EnumSet.noneOf(CompareOptions.class)));
        assertArrayEquals(values, new String[]{
                " ccc",
                "BBB",
                "aaa"
        });
    }

    public Comparator<String> comparatorGenerator(EnumSet<CompareOptions> options) {
        return (x, y) -> {
            if (options.contains(CASE_INSENSITIVE)) {
                x = x.toLowerCase();
                y = y.toLowerCase();
            }
            if (options.contains(CompareOptions.SPACE_INSENSITIVE)) {
                x = x.replaceAll("\\s+", "");
                y = y.replaceAll("\\s+", "");
            }
            return options.contains(REVERSE) ? y.compareTo(x) : x.compareTo(y);
        };
    }

    enum CompareOptions {
        REVERSE,
        CASE_INSENSITIVE,
        SPACE_INSENSITIVE
    }
}
