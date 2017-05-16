package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/15.
 */
public class Client {
    public static void main(String[] args) {
        Soldier sanMao = new Soldier();
        sanMao.setGun(new Rifle());
        sanMao.killEnemy();

        Snipper siMao = new Snipper();
        siMao.setRifle(new AUG());
        siMao.killEnemy();
    }
}
