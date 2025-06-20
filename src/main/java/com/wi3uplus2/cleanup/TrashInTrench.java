package com.wi3uplus2.cleanup;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.SQLException;
import java.util.*;

public class TrashInTrench extends Game {

    @FXML private AnchorPane rootPane;
    @FXML private ProgressBar progressBar;
    @FXML private Label countdownLabel;
    @FXML private Label scoreLabel;
    @FXML private Button btnOrganik, btnAnorganik, btnB3;
    @FXML private Label finishedLabel;
    @FXML private Pane lane1, lane2, lane3, lane4;
    @FXML private Pane preGame;
    @FXML private Pane limitLine;

    private int score = 0;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private Timeline progressTimeline;
    private final Random random = new Random();
    private final Pane[] lanes = new Pane[4];
    private final List<Timeline> trashTimelines = new ArrayList<>();
    private Timeline spawnTimeline;
    private int moveSpeed = 3;
    private double spawnInterval = 2.0;
    private int nextLaneIndex = 0;

    @FXML
    public void initialize() {
        gameEnded = false;
        switch (GameState.difficulty) {
            case "easy": moveSpeed = 3; spawnInterval = 2.0; break;
            case "medium": moveSpeed = 4; spawnInterval = 1.5; break;
            case "hard": moveSpeed = 5; spawnInterval = 1.0; break;
        }

        lanes[0] = lane1;
        lanes[1] = lane2;
        lanes[2] = lane3;
        lanes[3] = lane4;

        updateScoreLabel();

        btnOrganik.setOnAction(e -> handleButtonPress("organik"));
        btnAnorganik.setOnAction(e -> handleButtonPress("anorganik"));
        btnB3.setOnAction(e -> handleButtonPress("B3"));

        disableButtons(true);
        finishedLabel.setVisible(false);
    }

    public void onFirstClick() {
        preGame.setVisible(false);
        countdownLabel.setVisible(true);
        startCountdown(countdownLabel);
        startGame();
    }

    private void handleButtonPress(String type) {
        for (Pane lane : lanes) {
            if (!lane.getChildren().isEmpty()) {
                ImageView trash = (ImageView) lane.getChildren().get(0);
                if (!"ready".equals(trash.getUserData())) continue; // Belum bisa ditekan
                String name = (String) trash.getId();
                if (name.startsWith(type)) {
                    score += 10;
                } else {
                    score -= 5;
                }
                updateScoreLabel();
                lane.getChildren().remove(trash);
                return;
            }
        }
    }

    @Override
    public void onCountdownEnd(Label label) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            TransitionScreenController controller = loader.getController();
            controller.show();
            Scene scene = label.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startGame() {
        gameStarted = true;
        score = 0;
        updateScoreLabel();
        setupProgressBar();
        disableButtons(false);
        finishedLabel.setVisible(false);
        startSpawningTrash();
    }

    private void startSpawningTrash() {
        spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(spawnInterval), e -> spawnTrash()));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();

        progressTimeline.setOnFinished(ev -> {
            stopAll();
            if (score <= 0) {
                AudioController.lose();
                lose();
            } else {
                AudioController.win();
                win();
            }
            endLevel();
        });
    }

    private void spawnTrash() {
        Pane lane = lanes[nextLaneIndex];
        nextLaneIndex = (nextLaneIndex + 1) % 4;

        String[] trashTypes = {
                "anorganik_plasticBag", "anorganik_plasticBottle", "anorganik_snackPack", "anorganik_sodaCan",
                "B3_Battery", "B3_brokenGlass", "B3_brokenLamp", "B3_poison",
                "organik_banana", "organik_dryLeaf", "organik_stick"
        };
        String name = trashTypes[random.nextInt(trashTypes.length)];

        ImageView trash = new ImageView(new Image(getClass().getResourceAsStream(
                "/com/wi3uplus2/cleanup/assets/images/object/" + name + ".png")));
        trash.setFitWidth(80);
        trash.setFitHeight(80);
        trash.setLayoutX(0);
        trash.setLayoutY(10);
        trash.setId(name);
        trash.setUserData("waiting"); // Belum bisa diproses

        lane.getChildren().add(trash);

        // Atur agar bisa diproses setelah 1 detik
        Timeline enable = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            trash.setUserData("ready");
        }));
        enable.play();

        Timeline move = new Timeline();
        move.getKeyFrames().add(new KeyFrame(Duration.millis(30), ev -> {
            trash.setLayoutX(trash.getLayoutX() + moveSpeed);
            if (trash.getBoundsInParent().intersects(limitLine.getBoundsInParent())) {
                lane.getChildren().remove(trash);
                move.stop();
                endLevelWithMiss();
                gameEnded = true;
            }
        }));
        move.setCycleCount(Animation.INDEFINITE);
        move.play();
        trashTimelines.add(move);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor: " + score);
    }

    private void setupProgressBar() {
        if (progressTimeline != null) progressTimeline.stop();
        progressBar.setProgress(0.0);

        final Integer[] timeLeft = {10};
        countdownLabel.setText("Waktu: 10");

        Timeline labelTimer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            timeLeft[0]--;
            countdownLabel.setText("Waktu: " + timeLeft[0]);
        }));
        labelTimer.setCycleCount(10);
        labelTimer.play();

        progressTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), new KeyValue(progressBar.progressProperty(), 1.0))
        );

        progressTimeline.setOnFinished(ev -> {
            stopAll();
            labelTimer.stop();
            if (score <= 0) {
                AudioController.lose();
                lose();
            } else {
                AudioController.win();
                win();
            }
            endLevel();
        });

        progressTimeline.play();
    }

    private void endLevel() {
        stopAll();
        disableButtons(true);
        gameStarted = false;
        finishedLabel.setVisible(true);
        clearAllTrash();
    }

    private void endLevelWithMiss() {
        if (gameEnded) return;
        gameEnded = true;
        stopAll();
        disableButtons(true);
        gameStarted = false;
        clearAllTrash();
    }

    private void stopAll() {
        if (spawnTimeline != null) spawnTimeline.stop();
        if (progressTimeline != null) progressTimeline.stop();
        for (Timeline t : trashTimelines) t.stop();
    }

    private void clearAllTrash() {
        for (Pane lane : lanes) {
            lane.getChildren().clear();
        }
    }

    private void disableButtons(boolean disable) {
        btnOrganik.setDisable(disable);
        btnAnorganik.setDisable(disable);
        btnB3.setDisable(disable);
    }

    @Override
    void win() {
        try {
            DatabaseHandler.insertMinigameSessionData(5, true);
            GameState.currentScore += score;
        } catch (SQLException e) {
            System.err.println("Error inserting minigame session data: " + e.getMessage());
        }
    }

    @Override
    void lose() {
        if (gameEnded) return;
        gameEnded = true;
        try {
            DatabaseHandler.insertMinigameSessionData(5, false);
            GameState.currentLives--;
            GameState.currentScore += score;
        } catch (SQLException e) {
            System.err.println("Error inserting minigame session data: " + e.getMessage());
        }
    }
}