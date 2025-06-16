package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class CleanUpController {

    @FXML
    public Button startButton;

    @FXML
    protected void onStartButtonClick(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            System.out.println(loader.getLocation());
            Parent root = loader.load();

            // Get the controller and call init()
            GameState.currentScore = 0;
            GameState.currentLives = 3;
            TransitionScreenController controller = loader.getController();
            controller.show();
            DatabaseHandler.initSession();

            // Set the new root for the current scene
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}