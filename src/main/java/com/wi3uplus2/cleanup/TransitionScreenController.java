package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TransitionScreenController {

    private Parent[] minigames = new Parent[3];
    private ChaseThatStupidGuy chaseThatStupidGuy;
    private SortTheTrash sortTheTrash;
    private GrowtheForest growtheForest;
    @FXML
    public Label lives;
    @FXML
    public Label score;
    @FXML
    public ImageView nextGameButton;
    @FXML
    public Label nextGameLabel;

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

        // initialize minigame 3
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-GrowtheForest.fxml"));
            minigames[2] = loader.load();
            growtheForest = loader.getController();
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
    protected void onButtonClick(javafx.scene.input.MouseEvent event) {
        AudioController.click();
        System.out.println(GameState.nextGame);
        if (GameState.nextGame < 3) {
            GameState.nextGame++;
        } else {
            GameState.nextGame = 0;
        }
        if (GameState.currentLives > 0){
            try {
                // Set the new root for the current scene
                Scene scene = lives.getScene();
                switch (GameState.nextGame) {
                    case 0:
//                        chaseThatStupidGuy.startCountdown(chaseThatStupidGuy.countdownLabel);
                        break;
                    case 1:
//                        sortTheTrash.startCountdown();
                        break;
                    case 2:
//                        growtheForest.startCountdown(growtheForest.countdownLabel);
                        break;
                }

                scene.setRoot(minigames[2]);
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

    public void onButtonHover() {
        nextGameButton.setX(nextGameButton.getX() - 10);
        nextGameButton.setY(nextGameButton.getY() - 10);

        nextGameButton.setFitWidth(nextGameButton.getFitWidth() + 20);
        nextGameButton.setFitHeight(nextGameButton.getFitHeight() + 20);

        nextGameLabel.setLayoutX(nextGameLabel.getLayoutX() + 10);
    }

    public void onButtonExit() {
        nextGameButton.setX(nextGameButton.getX() + 10);
        nextGameButton.setY(nextGameButton.getY() + 10);

        nextGameButton.setFitWidth(nextGameButton.getFitWidth() - 20);
        nextGameButton.setFitHeight(nextGameButton.getFitHeight() - 20);

        nextGameLabel.setLayoutX(nextGameLabel.getLayoutX() - 10);
    }


    protected void pickNextGame() {
        if ((int)(Math.random() * 2 ) != GameState.nextGame) {
            GameState.nextGame = (int) (Math.random() * 2);
        } else {
            pickNextGame();
        }
    }
}
