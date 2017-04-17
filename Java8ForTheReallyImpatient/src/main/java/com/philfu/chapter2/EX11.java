package com.philfu.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/14.
 */
public class EX11 implements Exercise {
    @Override
    @Test
    public void perform() {
        List<ArrayList<String>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList("01", "02", "03")));
        list.add(new ArrayList<>(Arrays.asList("04", "05")));
        list.add(new ArrayList<>(Arrays.asList("06", "07", "08", "09", "10")));
        assertThat(collect(list.stream()).size(), is(10));
    }

    public List<String> collect(Stream<ArrayList<String>> stream) {
        String[] array = stream.flatMap(Collection::stream).toArray(String[]::new);
        IntStream range = IntStream.range(0, array.length);
        List<String> result = Arrays.asList(new String[array.length]);
        range.parallel().forEach(e -> result.set(e, array[e]));
        return result;
    }
}
