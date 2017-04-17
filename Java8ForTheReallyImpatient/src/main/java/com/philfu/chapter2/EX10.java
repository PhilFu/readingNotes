package com.philfu.chapter2;

import java.util.stream.Stream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/14.
 */
public class EX10 implements Exercise {
    @Override
    @Test
    public void perform() {
        assertThat(calcAverage(Stream.of(2.0, 4.0, 6.0, 8.0)), is(5.0));
        assertThat(calcAverage(Stream.of(2.0, 4.0, 6.0, 8.0).parallel()), is(5.0));
    }

    public Double calcAverage(Stream<Double> stream) {
        return stream.reduce(new Averager(), Averager::accept, Averager::combine).average();
    }
}

class Averager {
    private final long count;
    private final Double value;

    Averager() {
        this.count = 0;
        this.value = 0.0;
    }

    Averager(long count, Double value) {
        this.count = count;
        this.value = value;
    }

    Averager accept(Double value) {
        return new Averager(this.count + 1, this.value + value);
    }

    Averager combine(Averager averager) {
        return new Averager(this.count + averager.getCount(), this.value + averager.getValue());
    }

    double average() {
        return count == 0 ? 0.0 : this.value / this.count;
    }

    public long getCount() {
        return count;
    }

    public Double getValue() {
        return value;
    }
}