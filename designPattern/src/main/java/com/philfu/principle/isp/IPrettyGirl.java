package com.philfu.principle.isp;

/**
 *
 * 接口过于庞大,容纳了一些可变的因素, 把美貌、身材、气质整合到一个接口中,如果将来区分气质美女和外形美女,无法适应
 * 可拆分成IGreatTemperamentGirl和IGoodBodyGirl两个
 * 仅为示例,注意设计是有限度的,不能无限考虑未来的变化,否则会陷入设计的泥潭不能自拔
 *
 * Created by fuweiwei02 on 2017/5/25.
 */
public interface IPrettyGirl {
    void goodLooking();

    void niceFigure();

    void greatTemperament();
}
