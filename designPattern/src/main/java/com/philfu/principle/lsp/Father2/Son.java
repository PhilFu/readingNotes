package com.philfu.principle.lsp.Father2;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Son extends Father {
    // 该方法是重载(Overload),不是重写(Override), 加@Override会报错
    public Collection doSomething(HashMap map) {
        System.out.println("子类被执行...");
        return map.values();
    }
}
