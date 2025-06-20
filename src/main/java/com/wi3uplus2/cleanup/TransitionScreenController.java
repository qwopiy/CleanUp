package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TransitionScreenController {

    private Parent[] minigames = new Parent[5];
  
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
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }

        // initialize minigame 2
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-SortTheTrash.fxml"));
            minigames[1] = loader.load();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }

        // initialize minigame 3
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-GrowtheForest.fxml"));
            minigames[2] = loader.load();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }

        //initialize minigame 4
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-DefendTheGarden.fxml"));
            minigames[3] = loader.load();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }

        // Initialize minigame 5
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-TrashInTrench.fxml"));
            minigames[4] = loader.load();
        } catch (Exception e) {
            System.out.println("Error loading minigames scene: " + e.getMessage());
        }
    }

    @FXML
    public void show() {
        // Initialize the transition screen with default values
        lives.setText("Nyawa : " + GameState.currentLives);
        score.setText("Skor  : " + GameState.currentScore);
    }

    @FXML
    protected void onButtonClick(javafx.scene.input.MouseEvent event) {
        AudioController.click();

        // difficulty check
        if (GameState.currentScore < 100) {
            GameState.difficulty = "easy";
        } else if (GameState.currentScore < 300) {
            GameState.difficulty = "medium";
        } else {
            GameState.difficulty = "hard";
        }

        // memilih minigame selanjutnya
        pickNextGame();

        // Set the next game to play
        if (GameState.currentLives > 0){
            try {
                // Set the new root for the current scene
                Scene scene = lives.getScene();
                scene.setRoot(minigames[GameState.nextGame]);
            } catch (Exception e) {
                System.out.println(e + " " + GameState.nextGame);
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
                System.out.println(e + " " + "mainmenu");
           }
        }
    }

    public void onButtonHover() {
        show();
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

    public void pickNextGame() {
        int randomGame = (int) (Math.random() * minigames.length);
        if ((randomGame) != GameState.nextGame) {
            GameState.nextGame = randomGame;
            System.out.println("Next game is: " + GameState.nextGame);
        } else {
            pickNextGame();
        }
    }
}
