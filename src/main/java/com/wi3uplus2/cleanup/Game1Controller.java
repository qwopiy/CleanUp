package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Game1Controller {

    @FXML
    private ImageView player;
    @FXML
    private ImageView pembuangSampah;
    @FXML
    private Label countdownLabel;

    private String difficulty = "easy";
    private int playerSpeed = 10;

    private int countdownSeconds = 10; // Set countdown duration
    private Timeline countdownTimeline;
//    private int animCycle = 0;
//    private Image[] playerRun;

    @FXML
    public void initialize() {
//        playerRun[0] = new Image(String.valueOf(getClass().getResource("assets/images/character/Character_Run1")));
//        playerRun[1] = new Image(String.valueOf(getClass().getResource("assets/images/character/Character_Run2")));

        if (GameState.currentScore > 100) {
            difficulty = "hard";
        } else if (GameState.currentScore > 50) {
            difficulty = "medium";
        }
        switch (difficulty) {
            case "easy":
                playerSpeed = 30;
                pembuangSampah.setX(pembuangSampah.getX() - 200);
                break;
            case "medium":
                playerSpeed = 25;
                player.setY(player.getY() + 50);
                pembuangSampah.setY(pembuangSampah.getY() + 50);
                pembuangSampah.setX(pembuangSampah.getX() - 100);
                break;
            case "hard":
                playerSpeed = 20;
                player.setY(player.getY() + 100);
                pembuangSampah.setY(pembuangSampah.getY() + 100);
                break;
            default:
                playerSpeed = 10; // Default speed
        }

        System.out.println(difficulty);
    }

    public void startCountdown() {
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
            DatabaseHandler.insertMinigameSessionData(1, false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            TransitionScreenController controller = loader.getController();
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

//        switch (animCycle) {
//            case 0:
//                player.setImage(playerRun[1]);
//                animCycle = 1;
//                break;
//            case 1:
//                player.setImage(playerRun[0]);
//                animCycle = 0;
//                break;
//        }
    }

    private void onPlayerTouchPembuangSampah() {
        // Code to run after player touches pembuangSampah
        System.out.println("Player touched pembuangSampah!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            countdownTimeline.stop();

            // Get the controller and update the score or lives
            TransitionScreenController controller = loader.getController();
            GameState.currentScore += 10; // Example of updating score
            controller.show();
            DatabaseHandler.insertMinigameSessionData(1, true);

            // Set the new root for the current scene
            Scene scene = player.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
