package com.philfu.principle.isp;

/**
 * Created by fuweiwei02 on 2017/5/25.
 */
public class Client {
    public static void main(String[] args) {
        IPrettyGirl yanyan = new PrettyGirl("嫣嫣");
        AbstractSearcher searcher = new Searcher(yanyan);
        searcher.show();
    }
}
