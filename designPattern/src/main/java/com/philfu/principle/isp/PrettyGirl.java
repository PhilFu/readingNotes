package com.philfu.principle.isp;

/**
 * Created by fuweiwei02 on 2017/5/25.
 */
public class PrettyGirl implements IPrettyGirl {

    private String name;

    public PrettyGirl(String name) {
        this.name = name;
    }

    @Override
    public void goodLooking() {
        System.out.println(this.name + "----脸蛋漂亮");
    }

    @Override
    public void niceFigure() {
        System.out.println(this.name + "----气质非常好");
    }

    @Override
    public void greatTemperament() {
        System.out.println(this.name + "----身材非常漂亮");
    }
}
