package com.philfu.principle.lsp.Father2;

import java.util.Collection;
import java.util.Map;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Father {
    public Collection doSomething(Map map) {
        System.out.println("父类被执行。。。");
        return map.values();
    }
}
