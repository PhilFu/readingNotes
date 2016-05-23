package com.philfu.chapter1;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import com.philfu.Exercise;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Q： Arrays.sort方法中的比较器代码的线程与调用sort的线程是同一个吗？
 * A： 是。Arrays.sort是单线程排序，Arrays.parallelSort会用到多个线程排序。
 *
 * Created by Phil on 2016/5/23.
 */
public class EX1 implements Exercise{

    @Override
    @Test
    public void perform() {
        final long currentThreadId = Thread.currentThread().getId();
        String[] array = getWordsAsArray();

        final CopyOnWriteArraySet<Long> threadIds = new CopyOnWriteArraySet<Long>();
        Arrays.sort(array, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });

        assertThat(threadIds, hasSize(1));
        assertThat(threadIds, hasItem(currentThreadId));

        threadIds.clear();
        Arrays.parallelSort(array, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });

        threadIds.stream().forEach(System.out::println);
    }
}
