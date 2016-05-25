package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * Q: 为什么你不能直接使用Callable<Void>来代替RunnableEx？
 * A: 使用Callable<Void>编译报错，必须有一个返回值，即使他是Void的
 *
 * Created by Phil on 2016/5/25.
 */
public class EX6 implements Exercise{

    @Override
    @Test
    public void perform() {
        new Thread(uncheck(() -> {
            System.out.println("hello");
        })).start();

//        new Thread(uncheckCall(() -> {
//            System.out.println("hi");
//        })).start();
    }

    public static Runnable uncheck(RunnableEx runEx) {
        return () -> {
            try {
                runEx.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable uncheckCall(Callable<Void> callEx) {
        return () -> {
            try {
                callEx.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}

@FunctionalInterface
interface RunnableEx {
    void run() throws Exception;
}
