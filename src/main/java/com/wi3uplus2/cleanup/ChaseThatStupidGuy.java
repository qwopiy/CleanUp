package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class ChaseThatStupidGuy extends Game{

    @FXML
    private ImageView player;
    @FXML
    private ImageView pembuangSampah;
    @FXML
    public Label countdownLabel;

    private String difficulty = "easy";
    private int playerSpeed = 10;

//    private int animCycle = 0;
//    private Image[] playerRun;

    @Override
    void win() throws SQLException {
        GameState.currentScore += 10;
        DatabaseHandler.insertMinigameSessionData(1, true);
    }

    @Override
    void lose() throws SQLException {
        GameState.currentLives--;
        DatabaseHandler.insertMinigameSessionData(1, false);
    }

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

}
