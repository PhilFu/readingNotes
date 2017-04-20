package com.philfu.chapter3;

import java.util.concurrent.locks.ReentrantLock;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/19.
 */
public class EX2 implements Exercise {
    @Override
    public void perform() {
        withLock(new ReentrantLock(), () -> System.out.println("some locked action"));
    }

    public void withLock(ReentrantLock myLock, Runnable t) {
        myLock.lock();
        try {
            t.run();
        } finally {
            myLock.unlock();
        }
    }
}
