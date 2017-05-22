package com.philfu.principle.dip;

/**
 * Created by fuweiwei02 on 2017/5/22.
 */
public class Client {
    public static void main(String[] args) {
        ICar benz = new Benz();
        IDriver zhangSan = new Driver();
        zhangSan.drive(benz);

        ICar bmw = new BMW();
        zhangSan.drive(bmw);
    }
}
