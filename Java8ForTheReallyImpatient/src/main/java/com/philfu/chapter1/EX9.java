package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by DeBen on 2016/5/28.
 */
public class EX9 implements Exercise{

    @Override
    @Test
    public void perform() {
        Collection2<Integer> c = new ArrayList2<>();
        c.add(100);
        c.add(-5);

        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        c.forEachIf(set::add, e -> e > 0);

        assertThat(set.size(), is(1));
        assertThat(set.toArray()[0], is(100));
    }
}

interface Collection2<T> extends Collection<T> {
    default void forEachIf(Consumer<T> action, Predicate<T> filter) {
        forEach(e -> {
            if (filter.test(e)) {
                action.accept(e);
            }
        });
    }
}

class ArrayList2<T> extends ArrayList<T> implements Collection2<T> {

}
