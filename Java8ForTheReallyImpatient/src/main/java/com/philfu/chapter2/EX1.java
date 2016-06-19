package com.philfu.chapter2;

import com.philfu.Exercise;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by DeBen on 2016/6/19.
 */
public class EX1 implements Exercise{

    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();
        assertThat(words.stream().filter(w -> w.length() >12).count(), is(33L));
    }

    @Test
    public void countCurrentWithoutStream() {
        List<String> words = getWordsAsList();
        int coreSize = Runtime.getRuntime().availableProcessors();
        int chunkSize = words.size() / coreSize;

        List<List<String>> chunks = new LinkedList<>();
        for (int i = 0; i < words.size(); i+= chunkSize) {
            chunks.add(words.subList(i, i + Math.min(chunkSize, words.size() - i)));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(coreSize);
        Set<Future<Long>> result = new HashSet<>();

        for (List<String> strings : chunks) {
            Callable<Long> callable = () -> {
                long count = 0;
                for (String string : strings) {
                    if (string.length() > 12) {
                        count++;
                    }
                }
                return count;
            };

            Future<Long> future = executorService.submit(callable);
            result.add(future);
        }

        long count = 0;
        for (Future<Long> future : result) {
            try {
                count += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        assertThat(count, is(33L));
        System.out.println("words length: " + words.size());
        System.out.println("process size: " + coreSize);
        System.out.println("chunkSize: " + chunkSize);
        System.out.println("Chunks part: " + chunks.size());
    }
}
