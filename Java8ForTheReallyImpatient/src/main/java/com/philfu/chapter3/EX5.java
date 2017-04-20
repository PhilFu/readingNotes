package com.philfu.chapter3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fuweiwei02 on 2017/4/19.
 */
public class EX5 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image, (x, y, t) -> (
            x <= 10 || x >= image.getWidth() || y <= 10 || y >= image.getHeight() ? Color.GRAY : t)
        );
        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(newImage))));
        primaryStage.show();
    }

    public static Image transform(Image image, ColorTransformer t) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, t.apply(x, y, image.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }
}

@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color colorAtXY);
}