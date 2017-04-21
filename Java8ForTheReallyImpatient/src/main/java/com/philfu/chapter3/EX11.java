package com.philfu.chapter3;

import java.util.function.UnaryOperator;

import com.philfu.Exercise;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by fuweiwei02 on 2017/4/21.
 */
public class EX11 implements Exercise {
    @Override
    public void perform() {
        Image image = new Image("");
        Image newImage = EX5.transform(image, compose(trans(Color::brighter), (x, y, c) -> (
                x <= 10 || x >= image.getWidth() - 10 || y <= 10 || y >= image.getHeight() - 10) ? Color.GREY : c));
    }

    public static ColorTransformer compose(ColorTransformer first, ColorTransformer second) {
        return (x, y, t) -> second.apply(x, y, first.apply(x, y, t));
    }

    public static ColorTransformer trans(UnaryOperator<Color> c) {
        return (x, y, t) -> c.apply(t);
    }
}
