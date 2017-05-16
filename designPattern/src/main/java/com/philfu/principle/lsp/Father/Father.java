package com.philfu.principle.lsp.Father;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Father {
    public Collection doSomething(HashMap map) {
        System.out.println("父类被执行");
        return map.values();
    }
}
