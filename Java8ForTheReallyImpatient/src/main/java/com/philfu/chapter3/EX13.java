package com.philfu.chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fuweiwei02 on 2017/4/21.
 */
public class EX13 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private EnhancedColorTransformer blur() {
        return (x, y, c) -> {
            double r = 0, g = 0, b = 0;
            int counter = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < c.length && y + j < c[x + i].length) {
                        Color color = c[x + i][y + j];
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        counter++;
                    }
                }
            }
            return Color.color(r/counter, g/counter, b/counter);
        };
    }

    private EnhancedColorTransformer edgeDetection() {
        Color nullColor = Color.color(0.0, 0.0, 0.0);
        return (x, y, colors) -> {
            Color c = colors[x][y];
            Color n = y > 0 ? colors[x][y - 1] : nullColor;
            Color e = x < colors.length - 1 ? colors[x + 1][y] : nullColor;
            Color s = y < colors[y].length - 1 ? colors[x][y + 1] : nullColor;
            Color w = x > 0 ? colors[x - 1][y] : nullColor;
            double r = 4 * c.getRed() - n.getRed() - e.getRed() - s.getRed() - w.getRed();
            double g = 4 * c.getGreen() - n.getGreen() - e.getGreen() - s.getGreen() - w.getGreen();
            double b = 4 * c.getBlue() - n.getBlue() - e.getBlue() - s.getBlue() - w.getBlue();
            return Color.color(
                r < 0 ? 0.0 : r > 1 ? 1.0 : r,
                g < 0 ? 0.0 : g > 1 ? 1.0 : g,
                b < 0 ? 0.0 : b > 1 ? 1.0 : b
            );
        };
    }
}

class EnhancedLatentImage {
    private Image in;
    private List<EnhancedColorTransformer> pendingOperations;   // 将操作先累积起来

    EnhancedLatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add((x, y, c) -> f.apply(c[x][y]));
        return this;
    }

    EnhancedLatentImage transform(EnhancedColorTransformer c) {
        pendingOperations.add(c);
        return this;
    }

    public static EnhancedLatentImage from(Image image) {
        EnhancedLatentImage result = new EnhancedLatentImage();
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
                for (EnhancedColorTransformer f : pendingOperations) {
                    c = f.apply(x, y, c[x][y]);
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        }
        return out;
    }
}

@FunctionalInterface
interface EnhancedColorTransformer {
    Color apply(int x, int y, Color[][] c);
}
