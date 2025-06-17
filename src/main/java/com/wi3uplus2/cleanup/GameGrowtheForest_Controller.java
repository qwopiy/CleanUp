package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameGrowtheForest_Controller extends Game {
    @FXML
    private ImageView treeTemplate;
    @FXML
    private GridPane forestGrid;
    @FXML
    public Label countdownLabel;

    private String difficulty = "easy";
    private List<Point> treePositions = new ArrayList<>();
    Random rand = new Random();

    private int seconds = 8;
    private int spawnRate = 8; // Number of trees to spawn per click
    private int treesGrown = 0;

    private int id_minigame = 2;
    private int id_achievement_ForestSavior = 2;


    @FXML
    public void initialize() {
        if (GameState.currentScore >= 500) {
            difficulty = "hard";
        } else if (GameState.currentScore > 300) {
            difficulty = "medium";
        }

        switch (difficulty) {
            case "easy":
                break;
            case "medium":
                spawnRate = 4;
                seconds = 7;
                break;
            case "hard":
                spawnRate = 2;
                seconds = 6;
                break;
            default:
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                treePositions.add(new Point(i, j));
            }
        }
    }

    @Override
    public void startCountdown(Label label) {
        label.setText("Time: " + seconds);
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds--;
                    label.setText("Time: " + seconds);

                    if (treePositions.isEmpty()) {
                        GridFull();
                    }

                    if (seconds <= 0) {
                        countdownTimeline.stop();
                        this.onCountdownEnd(label);
                    }
                })
        );
        countdownTimeline.setCycleCount(seconds);
        countdownTimeline.play();
    }

    public void onClick() {
        for (int i = 0; i < spawnRate; i++) {
            int randomIndex = rand.nextInt(treePositions.size());
            Point chosenCell = treePositions.remove(randomIndex);
            int col = chosenCell.x;
            int row = chosenCell.y;

            ImageView tree = new ImageView(treeTemplate.getImage());
            tree.setFitWidth(360);
            tree.setFitHeight(180);
            tree.setPreserveRatio(true);

            forestGrid.add(tree, col, row);
            treesGrown++;
        }
    }

    private void GridFull(){
        try {
            win();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            countdownTimeline.stop();

            // Get the controller and update the score or lives
            TransitionScreenController controller = loader.getController();
            controller.show();

            // Set the new root for the current scene
            Scene scene = treeTemplate.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    void win() throws SQLException {
        GameState.currentScore += 50;
        DatabaseHandler.checkPlayerAchievements(id_achievement_ForestSavior, treesGrown, 1000);
        DatabaseHandler.insertMinigameSessionData(id_minigame, true);
    }

    @Override
    void lose() throws SQLException {
        GameState.currentLives--;
        DatabaseHandler.checkPlayerAchievements(id_achievement_ForestSavior, treesGrown, 1000);
        DatabaseHandler.insertMinigameSessionData(id_minigame, false);
    }
}
