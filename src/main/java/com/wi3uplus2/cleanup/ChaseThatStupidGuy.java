package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class ChaseThatStupidGuy extends Game{

    @FXML
    private ImageView player;
    @FXML
    private ImageView pembuangSampah;
    @FXML
    public Label countdownLabel;
    @FXML
    private Pane preGame;

    private int playerSpeed = 10;

    @Override
    void win() throws SQLException {
        AudioController.win();
        GameState.currentScore += 30;
        DatabaseHandler.insertMinigameSessionData(1, true);
        DatabaseHandler.checkPlayerAchievements(1, 0, 0);
        DatabaseHandler.checkPlayerAchievements(6, 1,100);
    }

    @Override
    void lose() throws SQLException {
        AudioController.lose();
        GameState.currentLives--;
        DatabaseHandler.insertMinigameSessionData(1, false);
        DatabaseHandler.checkPlayerAchievements(1, 0, 0);
        DatabaseHandler.checkPlayerAchievements(6, 1,100);
    }

    @FXML
    public void initialize() {
        switch (GameState.difficulty) {
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
    }

    public void onFirstClick() {
        preGame.setVisible(false);
        countdownLabel.setVisible(true);
        startCountdown(countdownLabel);
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
            win();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            System.out.println(loader.getLocation());
            Parent root = loader.load();
            countdownTimeline.stop();

            // Get the controller and update the score or lives
            TransitionScreenController controller = loader.getController();
            controller.show();

            // Set the new root for the current scene
            Scene scene = player.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    @Override
//    public void onCountdownEnd(Label label) {
//        // Switch to transition screen or show game over
//        try {
//            lose();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
//            Parent root = loader.load();
//            TransitionScreenController controller = loader.getController();
//            controller.show();
//            Scene scene = label.getScene();
//            scene.setRoot(root);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
