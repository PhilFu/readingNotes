package com.philfu.principle.ocp;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * Created by fuweiwei02 on 2017/6/1.
 */
public class OffNovelBookTest {
    private IBook below40NovelBook = new OffNovelBook("平凡的世界", 3000, "路遥");
    private IBook above40NovelBook = new OffNovelBook("平凡的世界", 6000, "路遥");

    @Test
    public void testGetPriceBelow40() {
        assertThat(this.below40NovelBook.getPrice(), is(2400));
    }

    @Test
    public void testGetPriceAbove40() {
        assertThat(this.above40NovelBook.getPrice(), is(5400));
    }
}
