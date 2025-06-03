package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Game1 {

    @FXML
    private Rectangle player;
    @FXML
    private Rectangle pembuangSampah;
    @FXML
    private Label countdownLabel;

    private String difficulty = "easy";
    private int playerSpeed = 10;
    private int height = 200;
    private int width = 200;

    private int countdownSeconds = 10; // Set countdown duration
    private Timeline countdownTimeline;

    @FXML
    public void initialize() {
        startCountdown();
        if (GameState.currentScore > 100) {
            difficulty = "hard";
        } else if (GameState.currentScore > 50) {
            difficulty = "medium";
        }
        switch (difficulty) {
            case "easy":
                playerSpeed = 10;
                break;
            case "medium":
                playerSpeed = 9;
                width = 150;
                height = 150;
                player.setY(player.getY() + 50);
                pembuangSampah.setY(pembuangSampah.getY() + 50);
                break;
            case "hard":
                playerSpeed = 8;
                width = 100;
                height = 100;
                player.setY(player.getY() + 100);
                pembuangSampah.setY(pembuangSampah.getY() + 100);
                break;
            default:
                playerSpeed = 10; // Default speed
        }
        // Set initial position and size of player and pembuangSampah
        player.setWidth(width);
        player.setHeight(height);
        pembuangSampah.setWidth(width);
        pembuangSampah.setHeight(height);

        System.out.println(difficulty);
    }

    private void startCountdown() {
        countdownLabel.setText("Time: " + countdownSeconds);
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    countdownSeconds--;
                    countdownLabel.setText("Time: " + countdownSeconds);
                    if (countdownSeconds <= 0) {
                        countdownTimeline.stop();
                        onCountdownEnd();
                    }
                })
        );
        countdownTimeline.setCycleCount(countdownSeconds);
        countdownTimeline.play();
    }

    private void onCountdownEnd() {
        GameState.currentLives--;
        // Switch to transition screen or show game over
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            TransitionScreen controller = loader.getController();
            controller.show();
            Scene scene = player.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onClick() {
        player.setX(player.getX() + playerSpeed);

        if (player.getBoundsInParent().intersects(pembuangSampah.getBoundsInParent())) {
            onPlayerTouchPembuangSampah();
        }
    }

    private void onPlayerTouchPembuangSampah() {
        // Code to run after player touches pembuangSampah
        System.out.println("Player touched pembuangSampah!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            countdownTimeline.stop();

            // Get the controller and update the score or lives
            TransitionScreen controller = loader.getController();
            GameState.currentScore += 10; // Example of updating score
            controller.show();

            // Set the new root for the current scene
            Scene scene = player.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
