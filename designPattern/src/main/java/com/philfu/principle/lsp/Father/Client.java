package com.philfu.principle.lsp.Father;

import java.util.HashMap;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Client {
    public static void invoker() {
        // 父类存在的地方,子类就应该能够存在
        Father f = new Father();
        HashMap map = new HashMap();
        f.doSomething(map);
    }

    public static void invoker2() {
        Son f = new Son();
        HashMap map = new HashMap();
        f.doSomething(map);
    }

    // 运行结果一样。如果想让子类的方法运行,就必须覆写父类的方法。
    public static void main(String[] args) {
        invoker();
        invoker2();
    }
}
