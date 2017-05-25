package com.philfu.principle.isp;

/**
 * Created by fuweiwei02 on 2017/5/25.
 */
public abstract class AbstractSearcher {

    protected IPrettyGirl prettyGirl;

    public AbstractSearcher(IPrettyGirl prettyGirl) {
        this.prettyGirl = prettyGirl;
    }

    public abstract void show();
}
