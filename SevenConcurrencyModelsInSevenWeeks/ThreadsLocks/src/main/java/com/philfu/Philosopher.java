package com.philfu;

import java.util.Random;

/**
 * Created by DeBen on 2016/8/2.
 */
public class Philosopher extends Thread{
    private ChopStick left, right;
    private Random random;

    public Philosopher(ChopStick left, ChopStick right) {
        this.left = left;
        this.right = right;
    }

    /*
    private ChopStick first, second;    // 按照固定的顺序获取，就能避免死锁
    public Philosopher(ChopStick left, ChopStick right) {
        if (left.getId() < right.getId()) {
            this.first = left;
            this.second = right;
        } else {
            this.first = right;
            this.second = left;
        }
    }
*/
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000)); //思考
                synchronized (left) {
                    synchronized (right) {
                        Thread.sleep(random.nextInt(1000)); //用餐
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ChopStick {
    int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
