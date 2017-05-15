package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/15.
 */
public class Rifle implements AbstractGun {
    @Override
    public void shoot() {
        System.out.println("步枪射击");
    }
}
