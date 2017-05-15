package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/15.
 */
public class HandGun implements AbstractGun {
    @Override
    public void shoot() {
        System.out.println("手枪射击。。。");
    }
}
