package com.philfu.chapter3;

import java.util.function.BiFunction;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Created by fuweiwei02 on 2017/4/20.
 */
public class EX6 {
    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T args) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y), args));
            }
        }
        return out;
    }

    public static void main(String[] args) {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image,
                (c, factor) -> c.deriveColor(0, 1, factor, 1), 1.5
        );
    }
}
