package com.philfu.principle.ocp;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * BookStore示例中,增加了一个 OffNoveBook 来适应变化,但是业务逻辑还是改了,修改了static静态区块的代码,
 * 但是该部分属于高层次的模块,是由持久层完成的。
 *
 * 在业务规则改变的情况下,高层模块必须由部分改变以适应新业务,改变要尽量的少,防止变化风险的扩散
 *
 * Created by fuweiwei02 on 2017/5/31.
 */
public class BookStore {
    private final static List<IBook> bookLists = new ArrayList<>();

    // static 静态模块初始化数据,实际中一般是由持久层完成
    static {
        bookLists.add(new NovelBook("天龙八部", 3200, "金庸"));
        bookLists.add(new NovelBook("巴黎圣母院", 5600, "雨果"));
        bookLists.add(new NovelBook("悲惨世界", 3500, "雨果"));
        bookLists.add(new NovelBook("金瓶梅", 4300, "兰陵笑笑生"));

        bookLists.add(new OffNovelBook("天龙八部", 3200, "金庸"));
        bookLists.add(new OffNovelBook("巴黎圣母院", 5600, "雨果"));
        bookLists.add(new OffNovelBook("悲惨世界", 3500, "雨果"));
        bookLists.add(new OffNovelBook("金瓶梅", 4300, "兰陵笑笑生"));

        bookLists.add(new ComputerBook("Think in Java", "编程语言", "Bruce Eckel", 4300));
    }

    public static void main(String[] args) {
        NumberFormat numberformat = NumberFormat.getCurrencyInstance();
        numberformat.setMaximumFractionDigits(2);
        System.out.println("-------------------书店卖出去的书籍记录如下: -------------------");
        for (IBook book : bookLists) {
            System.out.println("书籍名称:" + book.getName() + "\t书籍作者:" + book.getAuthor() + "\t书籍价格:"
                    + numberformat.format(book.getPrice() / 100.0) + "元");
        }
    }
}
