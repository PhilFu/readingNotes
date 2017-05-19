package com.philfu.principle.lsp.Father2;

import java.util.HashMap;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Client {
    public static void invoker() {
        Father f = new Father();
        HashMap map = new HashMap();
        f.doSomething(map);
    }

    public static void invoker2() {
        Son s = new Son();
        HashMap map = new HashMap();
        s.doSomething(map);
    }

    // 运行结果不一样。子类在没有覆写父类方法的前提下,子类方法被执行了,会引起业务逻辑混乱: 不符合里氏替换原则,父类出现的地方不能用子类替换
    // 子类中方法的前置条件必须与父类中被覆写的方法的前置条件相同或者更宽松
    public static void main(String[] args) {
        invoker();
        invoker2();
    }
}
