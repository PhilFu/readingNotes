package com.philfu.principle.ocp;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by fuweiwei02 on 2017/6/1.
 */
public class NovelBookTest {

    private String name = "平凡的世界";
    private int price = 6000;
    private String author = "路遥";
    private IBook novelBook = new NovelBook(name, price, author);

    @Test
    public void testGetPrice() {
        assertThat(this.price, equalTo(this.novelBook.getPrice()));
    }
}
