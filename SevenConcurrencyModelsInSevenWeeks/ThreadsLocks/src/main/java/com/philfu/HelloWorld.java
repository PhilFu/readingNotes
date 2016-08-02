package com.philfu;

/**
 * Created by DeBen on 2016/8/2.
 */
public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread() {
            public void run() {
                System.out.println("Hello from new thread.");
            }
        };

        myThread.start();
        Thread.yield();     // current thread (main) want to yield, so other thread(myThread) has chance to run.
        System.out.println("Hello from main thread.");
        myThread.join();    // wait myThread to complete
    }
}
