package com.philfu.chapter3;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/19.
 */
public class EX3 implements Exercise {
    @Override
    @Test
    public void perform() {
        // assert 关键字, 是个debug工具,需要使用 -ea(或 -enableassertions)开启
//        assert true;
//        assert false : "断言失败, 此表达式的信息将会在抛出异常的时候输出!";

//        assertThat(() -> 2 * 2 == 5);
        assertThat(() -> 2 * 2 == 5, () -> "断言失败, 此表达式的信息将会在抛出异常的时候输出!");
    }

    public void assertThat(BooleanSupplier assertion) {
        if (!assertion.getAsBoolean()) {
            throw new AssertionError();
        }
    }

    public void assertThat(BooleanSupplier assertion, Supplier<String> message) {
        if (!assertion.getAsBoolean()) {
            throw new AssertionError(message.get());
        }
    }
}
