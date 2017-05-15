package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/15.
 */
public class Soldier {
    private AbstractGun gun;

    public void setGun(AbstractGun gun) {
        this.gun = gun;
    }

    public void killEnemy() {
        System.out.println("士兵开始杀敌。。。");
        gun.shoot();
    }
}
