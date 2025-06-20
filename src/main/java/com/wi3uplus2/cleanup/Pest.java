package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Pest {
    private final ImageView image;
    private Timeline movement;

    public Pest(Image imageSource, double startX, double y, double speed, boolean fromLeft,
                AnchorPane pane, ImageView plant, Runnable onDeath, Runnable onReachPlant) {

        image = new ImageView(imageSource);
        image.setFitWidth(150);
        image.setFitHeight(150);
        image.setLayoutX(startX);
        image.setLayoutY(y);

        if (!fromLeft) {
            image.setScaleX(-1);
        }

        image.setOnMouseClicked(e -> {
            pane.getChildren().remove(image);
            movement.stop();
            onDeath.run();
        });

        movement = new Timeline(new KeyFrame(Duration.millis(20), ev -> {
            double direction = (plant.getLayoutX() - image.getLayoutX()) > 0 ? 1 : -1;
            image.setLayoutX(image.getLayoutX() + direction * speed);


            Bounds pestBounds = image.getBoundsInParent();
            Bounds plantBounds = plant.getBoundsInParent();

            // Ambil batas kiri dan kanan tanaman yang "dipersempit"
            double shrinkLeft = plantBounds.getMinX() + 75;
            double shrinkRight = plantBounds.getMaxX() - 60;

            // Ambil batas kiri dan kanan hama
            double pestLeft = pestBounds.getMinX();
            double pestRight = pestBounds.getMaxX();

            // Cek apakah hama masuk ke area tanaman yang dipersempit
            boolean overlaps = pestRight > shrinkLeft && pestLeft < shrinkRight;

            if (overlaps) {
                movement.stop();
                onReachPlant.run();
            }
        }));

        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();
        pane.getChildren().add(image);
    }

    public void stop() {
        movement.stop();
    }

    public ImageView getImageView() {
        return image;
    }
}