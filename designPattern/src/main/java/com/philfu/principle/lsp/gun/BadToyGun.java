package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/15.
 */
public class BadToyGun implements AbstractGun {
    @Override
    public void shoot() {
        // cannot shoot
        // 两种重构方法: 1. 在Sollider类中增加 instanceof 的判断,如果是BadToyGun,不用来杀敌。但每增加一个类,所有与这个父类有关系的类都必须修改。
        // 2. ToyGun脱离集成,建立一个独立的父类
    }
}
