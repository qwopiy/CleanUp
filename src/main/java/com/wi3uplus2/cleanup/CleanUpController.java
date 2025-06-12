package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CleanUpController {

    @FXML
    public Button startButton;
    public Label welcomeText;

    @FXML
    protected void onStartButtonClick(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();

            // Get the controller and call init()
            TransitionScreenController controller = loader.getController();
            controller.show();
            DatabaseHandler.initSession();
            GameState.currentScore = 0;
            GameState.currentLives = 3;

            // Set the new root for the current scene
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}