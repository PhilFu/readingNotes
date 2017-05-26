package com.philfu.principle.lod.Wizard;

import java.util.Random;

/**
 *
 * InstallSoftware 和 Wizard 耦合的太严重了, 如果修改 Wizard 的某个方法的返回值,就需要修改 InstallSoftware,把修改变更的风险扩散了
 * 在 Wizard 中增加一个 installWizard 方法,对安装过程进行封装,同时把三个public方法修改为private
 *
 * Created by fuweiwei02 on 2017/5/26.
 */
public class Wizard {

    private Random rand = new Random(System.currentTimeMillis());

    public int first() {
        System.out.println("执行第一个方法。。。");
        return rand.nextInt(100);
    }

    public int second() {
        System.out.println("执行第二个方法。。。");
        return rand.nextInt(100);
    }

    public int third() {
        System.out.println("执行第三个方法。。。");
        return rand.nextInt(100);
    }
}
