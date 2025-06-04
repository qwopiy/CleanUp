package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.Random;

public class TransitionScreenController {

    private String[] minigames = {"game-1.fxml", "game-2.fxml"};
    private int nextGame;
    @FXML
    public Label lives;
    @FXML
    public Label score;

    @FXML
    public void initialize() {
        // Randomly pick the next game to play
        pickNextGame();
        System.out.println("Next game: " + minigames[nextGame]);
    }

    @FXML
    public void show() {
        // Initialize the transition screen with default values
        lives.setText("Nyawa : " + GameState.currentLives);
        score.setText("Skor  : " + GameState.currentScore);
    }

    @FXML
    protected void onButtonClick(javafx.event.ActionEvent event) {
        if (GameState.currentLives > 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("game-1.fxml"));
                Parent root = loader.load();

                // Set the new root for the current scene
                Scene scene = ((Node) event.getSource()).getScene();
                scene.setRoot(root);
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

    void pickNextGame() {
        if ((int)(Math.random() * 2) != nextGame) {
            nextGame = (int) (Math.random() * 2);
        } else {
            pickNextGame();
        }
    }
}
