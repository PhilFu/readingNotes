package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class AUG extends Rifle {
    public void zoomOut() {
        System.out.println("通过望远镜观察敌人。。。");
    }

    @Override
    public void shoot() {
        System.out.println("AUG 射击");
    }
}
