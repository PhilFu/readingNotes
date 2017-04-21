package com.philfu.chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import com.philfu.Exercise;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fuweiwei02 on 2017/4/21.
 */
public class EX12 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("");
        Image finalImage = LatentImage.from(image)
                .transform(Color::brighter)
                .transform(Color::grayscale)
                .transform((x, y, c) -> (x <= 5 || x >= image.getWidth() - 5 || y <=5 || y >= image.getHeight() - 5)
                        ? Color.WHITE : c)
                .toImage();
    }
}

class LatentImage {
    private Image in;
    private List<ColorTransformer> pendingOperations;   // 将操作先累积起来

    LatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add((x, y, c) -> f.apply(c));
        return this;
    }

    LatentImage transform(ColorTransformer c) {
        pendingOperations.add(c);
        return this;
    }

    public static LatentImage from(Image image) {
        LatentImage result = new LatentImage();
        result.in = image;
        result.pendingOperations = new ArrayList<>();
        return result;
    }

    public Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = in.getPixelReader().getColor(x, y);
                for (ColorTransformer f : pendingOperations) {
                    c = f.apply(x, y, c);
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        }
        return out;
    }
}