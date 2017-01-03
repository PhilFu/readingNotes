package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

/**
 * 编写一个静态方法andThen，它接受两个Runnable实例作为参数，并返回一个分别运行这两个实例的Runnable对象。在main方法中，向andThen方法传递两个lambda表达式，并返回运行的实例。
 *
 * Created by Phil on 2016/5/27.
 */
public class EX7 implements Exercise{

    @Override
    @Test
    public void perform() {
        Runnable runnable = andThen(() -> {
            System.out.println("Runnable One is running.");
        }, () -> {
            System.out.println("Runnable Two is running");
        });
        new Thread(runnable).start();
    }

    private Runnable andThen(Runnable runnable1, Runnable runnable2) {
        return () -> {
            new Thread(runnable1).start();
            new Thread(runnable2).start();
        };
    }
}
