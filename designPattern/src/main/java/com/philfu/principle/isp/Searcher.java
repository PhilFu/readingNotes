package com.philfu.principle.isp;

/**
 * Created by fuweiwei02 on 2017/5/25.
 */
public class Searcher extends AbstractSearcher {

    public Searcher(IPrettyGirl prettyGirl) {
        super(prettyGirl);
    }

    @Override
    public void show() {
        System.out.println("--------美女信息如下:-----------");
        super.prettyGirl.goodLooking();
        super.prettyGirl.niceFigure();
        super.prettyGirl.greatTemperament();
    }
}
