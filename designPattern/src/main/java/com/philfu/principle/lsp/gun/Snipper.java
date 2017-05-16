package com.philfu.principle.lsp.gun;

/**
 * Created by fuweiwei02 on 2017/5/16.
 */
public class Snipper {

    private AUG rifle;

    public void setRifle(AUG rifle) {
        this.rifle = rifle;
    }
    public void killEnemy() {
        rifle.zoomOut();
        rifle.shoot();
    }
}
