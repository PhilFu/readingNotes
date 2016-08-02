package com.philfu;

/**
 * Created by DeBen on 2016/8/2.
 */
public class Counting {
    public static void main(String[] args) throws InterruptedException {
        class Counter {
            private int count = 0;
            public synchronized void increse() {
                count++;
            }
            public int getCount() {
                return count;
            }
        }

        final Counter counter = new Counter();
        class CounterThread extends Thread {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.increse();
                }
            }
        }

        Thread thread1 = new CounterThread();
        Thread thread2 = new CounterThread();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter.getCount());
    }
}
