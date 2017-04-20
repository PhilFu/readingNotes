package com.philfu.chapter3;

import static com.philfu.chapter3.EX5.transform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fuweiwei02 on 2017/4/20.
 */
public class EX8 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image, addFrame(image, 10, 5, Color.GREY));
        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(newImage))));
        primaryStage.show();
    }

    public static ColorTransformer addFrame(Image image, int xWidth, int yWidth, Color color) {
        return (x, y, t) ->
            (x <= xWidth || x >= image.getWidth() - xWidth || y <= yWidth || y>= image.getHeight() - yWidth ? color : t)
        ;
    }
}
