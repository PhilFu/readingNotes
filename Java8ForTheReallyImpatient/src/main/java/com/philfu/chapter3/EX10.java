package com.philfu.chapter3;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fuweiwei02 on 2017/4/20.
 */
public class EX10 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image, ((UnaryOperator<Color>)Color::brighter).compose(Color::grayscale));
        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(newImage))));
        primaryStage.show();
    }

    // UnaryOperator.compose 返回类型是Function, 所以不能直接compose, 必须用Function
    public static Image transform(Image in, Function<Color, Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }
}
