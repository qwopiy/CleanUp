package com.wi3uplus2.cleanup;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefendTheGarden_Controller extends Game {
    @Override
    void win() {
        GameState.currentScore += 50;
        try {
            DatabaseHandler.insertMinigameSessionData(1, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void lose() {
        GameState.currentLives--;
        try {
            DatabaseHandler.insertMinigameSessionData(1, false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView plantImage;

    @FXML
    private ImageView kumbang;

    @FXML
    private Label statusLabel;

    private int totalEnemies = 7;
    private int enemiesClicked = 0;
    private boolean gameEnded = false;
    private final List<Pest> pests = new ArrayList<>();
    private String difficulty = "normal";

    public void initialize() {
        // Tentukan difficulty berdasarkan skor dari GameState
        if (GameState.currentScore > 100) {
            difficulty = "hard";
        } else if (GameState.currentScore > 50) {
            difficulty = "medium";
        } else {
            difficulty = "easy";
        }

        System.out.println("Difficulty (auto from score): " + difficulty);

        // Spesifik untuk game Jagatanaman: totalEnemies & spawn berdasarkan difficulty
        switch (difficulty) {
            case "easy" -> totalEnemies = 5;
            case "medium" -> totalEnemies = 7;
            case "hard" -> totalEnemies = 10;
        }

        // Tunggu scene siap sebelum spawn enemy
        Platform.runLater(this::spawnEnemies);

//        setDifficulty("normal");
////        Platform.runLater(this::spawnEnemies);
//
//        Platform.runLater(() -> {
////            System.out.println("Scene width = " + gamePane.getScene().getWidth());
//            spawnEnemies();
//        });
    }

//    public void setDifficulty(String level) {
//        this.difficulty = level;
//        switch (level) {
//            case "easy" -> totalEnemies = 5;
//            case "normal" -> totalEnemies = 7;
//            case "hard" -> totalEnemies = 10;
//        }
//    }

    private double getSpeed() {
        return switch (difficulty) {
            case "easy" -> 4.0;
            case "normal" -> 5.0;
            case "hard" -> 6.0;
            default -> 4.0;
        };
    }

    private void spawnEnemies() {
        double spacing = 80;
        double y = plantImage.getLayoutY();
        double screenWidth = gamePane.getPrefWidth(); // atau getWidth() kalau sudah dirender
//        double screenWidth = gamePane.getScene().getWidth();

        for (int i = 0; i < totalEnemies; i++) {
            boolean fromLeft = (i % 2 == 0);
            double startX = fromLeft ? -(i + 1) * spacing : screenWidth + i * spacing;

            Pest pest = new Pest(
                    kumbang.getImage(),
                    startX,
                    y,
                    getSpeed(),
                    fromLeft,
                    gamePane,
                    plantImage,
                    () -> {
                        enemiesClicked++;
                            checkWin();

                    },
                    () -> {
                        lose();
                        endGame("You Lose!");
                    }
            );

            pests.add(pest);
        }
    }

    private void checkWin(){
        if (gameEnded) return;
        if (enemiesClicked == totalEnemies) {
            win();
            endGame("You Win!");
        }
    }

    private void endGame(String result) {
        if (gameEnded) return;
        gameEnded = true;

        statusLabel.setText(result);
        gamePane.setDisable(true);

        if (result.equals("You Win!")) {
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-size: 36px; -fx-font-weight: bold;");
        } else {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-size: 36px; -fx-font-weight: bold;");
        }

        for (Pest pest : pests) {
            pest.stop();
        }
    }
}
