package com.philfu.chapter2;

import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/3/23.
 */
public class EX8 implements Exercise {

    @Override
    @Test
    public void perform() {
        Stream<String> stream = zip(Stream.of("1", "2", "3"), Stream.of("a", "b" ,"c"));
        stream.forEach(System.out::println);
    }

    public <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> secondIterator = second.iterator();
        Stream.Builder<T> builder = Stream.builder();
        first.forEach(e -> {
            if (secondIterator.hasNext()) {
                builder.accept(e);
                builder.accept(secondIterator.next());
            } else {
                first.close();
            }
        });

        return builder.build();
    }
}
