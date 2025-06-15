package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Random;

public class Game3Controller {

    @FXML
    private ImageView background;
    @FXML
    private ImageView trashImage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label instructionLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem pauseMenu, replayMenu, exitMenu;
    @FXML
    private Button btnOrganik, btnAnorganik, btnB3;

    private int score = 0;
    private double progress = 0.0;
    private Timeline timeline;
    private String currentTrashType;

    private final String[] trashTypes = {"Organik", "Anorganik", "B3"};
    private final Random random = new Random();

    @FXML
    public void initialize() {
        updateScoreLabel();
        nextTrash();
        setupProgressBar();

        pauseMenu.setOnAction(e -> pauseGame());
        replayMenu.setOnAction(e -> restartGame());
        exitMenu.setOnAction(e -> exitGame());

        btnOrganik.setOnAction(e -> checkAnswer("Organik"));
        btnAnorganik.setOnAction(e -> checkAnswer("Anorganik"));
        btnB3.setOnAction(e -> checkAnswer("B3"));
    }

    private void setupProgressBar() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            progress += 0.01;
            progressBar.setProgress(progress);
            if (progress >= 1.0) {
                endLevel();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void nextTrash() {
        int index = random.nextInt(trashTypes.length);
        currentTrashType = trashTypes[index];

        // Placeholder image, replace with actual logic if needed
        trashImage.setImage(new Image(getClass().getResource("/assets/images/trash/" + currentTrashType + ".png").toExternalForm()));

        progress = 0.0;
        progressBar.setProgress(progress);
    }

    private void checkAnswer(String selected) {
        if (currentTrashType.equals(selected)) {
            score += 10;
        } else {
            score -= 5;
        }
        updateScoreLabel();
        nextTrash();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor: " + score);
    }

    private void endLevel() {
        timeline.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waktu Habis");
        alert.setHeaderText(null);
        alert.setContentText("Skor akhir kamu: " + score);
        alert.showAndWait();
        restartGame();
    }

    private void pauseGame() {
        timeline.pause();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Dijeda");
        alert.setHeaderText(null);
        alert.setContentText("Game dalam keadaan pause. Klik OK untuk melanjutkan.");
        alert.showAndWait();
        timeline.play();
    }

    private void restartGame() {
        score = 0;
        updateScoreLabel();
        nextTrash();
        timeline.playFromStart();
    }

    private void exitGame() {
        timeline.stop();
        // Tambahkan logika kembali ke menu utama jika perlu
    }
}
