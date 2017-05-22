package com.philfu.principle.dip;

/**
 * Created by fuweiwei02 on 2017/5/22.
 */
public class Driver implements IDriver {
    @Override
    public void drive(ICar car) {
        car.run();
    }
}
