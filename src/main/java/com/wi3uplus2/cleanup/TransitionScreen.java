package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransitionScreen {

    public int currentLives = 3;
    public int currentScore = 0;

    @FXML
    public Label lives;
    public Label score;

    @FXML
    public void init() {
        // Initialize the transition screen with default values
        lives.setText("Lives: " + currentLives);
        score.setText("Score: " + currentScore);
    }
}
