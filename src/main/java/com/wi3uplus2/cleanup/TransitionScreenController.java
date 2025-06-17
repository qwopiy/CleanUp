package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class TransitionScreenController {

    private Parent[] minigames = new Parent[2];
    private ChaseThatStupidGuy chaseThatStupidGuy;
    private SortTheTrash sortTheTrash;
    @FXML
    public Label lives;
    @FXML
    public Label score;

    @FXML
    public void initialize() {
        // initialize minigame 1
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-ChaseThatStupidGuy.fxml"));
            minigames[0] = loader.load();
            chaseThatStupidGuy = loader.getController();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }

        // initialize minigame 2
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-SortTheTrash.fxml"));
            minigames[1] = loader.load();
            sortTheTrash = loader.getController();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }
    }

    @FXML
    public void show() {
        // Randomly pick the next game to play
//        pickNextGame();

        // Initialize the transition screen with default values
        lives.setText("Nyawa : " + GameState.currentLives);
        score.setText("Skor  : " + GameState.currentScore);
    }

    @FXML
    protected void onButtonClick(javafx.event.ActionEvent event) {
        // Randomly pick the next game to play
        System.out.println(GameState.nextGame);
        if (GameState.nextGame == 0) {
            GameState.nextGame++;
        } else {
            GameState.nextGame--;
        }
        if (GameState.currentLives > 0){
            try {
                // Set the new root for the current scene
                Scene scene = ((Node) event.getSource()).getScene();
                switch (GameState.nextGame) {
                    case 0:
                        chaseThatStupidGuy.startCountdown(chaseThatStupidGuy.countdownLabel);
                        break;
                    case 1:
                        sortTheTrash.startCountdown();
                        break;
                }

                scene.setRoot(minigames[GameState.nextGame]);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                GameState.endGame();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
                Parent root = loader.load();

                // Set the new root for the current scene
                Scene scene = ((Node) event.getSource()).getScene();
                scene.setRoot(root);
            } catch (Exception e) {
                System.out.println(e);
           }
        }
    }

    protected void pickNextGame() {
        if ((int)(Math.random() * 2 ) != GameState.nextGame) {
            GameState.nextGame = (int) (Math.random() * 2);
        } else {
            pickNextGame();
        }
    }
}
