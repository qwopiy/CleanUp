package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class TransitionScreen {

    @FXML
    public Label lives;
    @FXML
    public Label score;

    @FXML
    public void show() {
        // Initialize the transition screen with default values
        lives.setText("Nyawa : " + GameState.currentLives);
        score.setText("Skor  : " + GameState.currentScore);
    }

    @FXML
    protected void onButtonClick(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-1.fxml"));
            Parent root = loader.load();

            // Set the new root for the current scene
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
