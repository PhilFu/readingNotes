package com.philfu.principle.ocp;

/**
 * Created by fuweiwei02 on 2017/6/1.
 */
public class ComputerBook implements IComputerBook {

    private String name;
    private String scope;
    private String author;
    private int price;

    public ComputerBook(String name, String scope, String author, int price) {
        this.name = name;
        this.scope = scope;
        this.author = author;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getScope() {
        return null;
    }
}
