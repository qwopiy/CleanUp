package com.wi3uplus2.cleanup;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.SQLException;
import java.util.*;

public class TrashInTrench extends Game{

    @FXML private AnchorPane rootPane;
    @FXML private ProgressBar progressBar;
    @FXML private Label countdownLabel;
    @FXML private Label scoreLabel;
    @FXML private Button btnOrganik, btnAnorganik, btnB3;
    @FXML private Label finishedLabel;
    @FXML private Pane lane1, lane2, lane3, lane4;
    @FXML private Pane preGame;

    private int score = 0;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private Timeline progressTimeline;
    private final Random random = new Random();
    private final Pane[] lanes = new Pane[4];
    private final List<Timeline> trashTimelines = new ArrayList<>();
    private Timeline spawnTimeline;
    private int moveSpeed = 3;
    private double spawnInterval = 2.0; // seconds

    @FXML
    public void initialize() {
        gameEnded = false;
        switch (GameState.difficulty) {
            case "easy":
                moveSpeed = 3;
                spawnInterval = 2.0;
                break;
            case "medium":
                moveSpeed = 4;
                spawnInterval = 1.5;
                break;
            case "hard":
                moveSpeed = 5;
                spawnInterval = 1.0;
                break;
        }

        lanes[0] = lane1;
        lanes[1] = lane2;
        lanes[2] = lane3;
        lanes[3] = lane4;

        updateScoreLabel();

        setupDropHandlers();

        disableButtons(true);
        finishedLabel.setVisible(false);
    }

    public void onFirstClick() {
        preGame.setVisible(false);
        countdownLabel.setVisible(true);
        startCountdown(countdownLabel);
        startGame();
    }

    private void setupDropHandlers() {
        setupDropHandler(btnOrganik, "organik");
        setupDropHandler(btnAnorganik, "anorganik");
        setupDropHandler(btnB3, "B3");
    }

    private void setupDropHandler(Button btn, String typePrefix) {
        btn.setOnDragOver(event -> {
            if (event.getGestureSource() != btn && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        btn.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                if (db.getString().startsWith(typePrefix)) {
                    score += 10;
                } else {
                    score -= 5;
                }
                updateScoreLabel();
                ImageView draggedTrash = (ImageView) event.getGestureSource();
                ((Pane) draggedTrash.getParent()).getChildren().remove(draggedTrash);
                draggedTrash.setUserData("handled");
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @Override
    public void onCountdownEnd(Label label) {
        // Switch to transition screen or show game over
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

        // win condition
        progressTimeline.setOnFinished(ev -> {
            if (spawnTimeline != null) spawnTimeline.stop();
            if (progressTimeline != null) progressTimeline.stop();
            for (Timeline t : trashTimelines) t.stop();

            if (score <= 0) {
                AudioController.lose();
                lose();
            }
            else {
                AudioController.win();
                win();
            }
            endLevel();
        });
    }

    private void spawnTrash() {
        int laneIndex = random.nextInt(4);
        Pane lane = lanes[laneIndex];

        String[] trashTypes = {
                "anorganik_plasticBag", "anorganik_plasticBottle", "anorganik_snackPack", "anorganik_sodaCan",
                "B3_Battery", "B3_brokenGlass", "B3_brokenLamp", "B3_poison",
                "organik_banana", "organik_dryLeaf", "organik_stick"
        };
        String name = trashTypes[random.nextInt(trashTypes.length)];

        Image trashImage = new Image(getClass().getResourceAsStream(
                "/com/wi3uplus2/cleanup/Asset/Trash/" + name + ".png"
        ));

        ImageView trash = new ImageView(trashImage);
        trash.setFitWidth(80);
        trash.setFitHeight(80);
        trash.setLayoutX(0);
        trash.setLayoutY(10);
        trash.setUserData("alive");

        lane.getChildren().add(trash);

        Timeline move = new Timeline();
        move.getKeyFrames().add(new KeyFrame(Duration.millis(30), ev -> {
            if ("alive".equals(trash.getUserData())) {
                trash.setLayoutX(trash.getLayoutX() + moveSpeed);
                if (trash.getLayoutX() > rootPane.getWidth()) {
                    lane.getChildren().remove(trash);
                    move.stop();
                    endLevelWithMiss();
                    gameEnded = true;
                }
            }
        }));
        move.setCycleCount(Animation.INDEFINITE);
        move.play();
        trashTimelines.add(move);

//        trash.setOnDragDetected(e -> {
//            if (!"handled".equals(trash.getUserData())) {
//                Dragboard db = trash.startDragAndDrop(TransferMode.MOVE);
//                ClipboardContent content = new ClipboardContent();
//                content.putString(name);
//                db.setContent(content);
//                e.consume();
//            }
//        });
//
//        trash.setOnMouseDragged(event -> {
//            if (!"handled".equals(trash.getUserData())) {
//                trash.setLayoutX(event.getSceneX() - 40);
//                trash.setLayoutY(event.getSceneY() - 40);
//                event.consume();
//            }
//        });

        trash.setOnMouseClicked(e -> {

        });
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor: " + score);
    }

    private void setupProgressBar() {
        if (progressTimeline != null) progressTimeline.stop();

        progressBar.setProgress(0.0);
        KeyValue keyValue = new KeyValue(progressBar.progressProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), keyValue);

        progressTimeline = new Timeline(keyFrame);
        progressTimeline.play();
    }

    private void endLevel() {
        spawnTimeline.stop();
        disableButtons(true);
        gameStarted = false;
        finishedLabel.setVisible(true);
        clearAllTrash();
    }

    private void endLevelWithMiss() {
        if (gameEnded) return; // Prevent multiple triggers
        gameEnded = true;
        if (gameStarted) {
            disableButtons(true);
            gameStarted = false;
            clearAllTrash();
            spawnTimeline.stop();
            if (progressTimeline != null) progressTimeline.stop();
        }
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
            GameState.currentScore += score; // Tambahkan skor ke total skor
        } catch (SQLException e) {
            System.err.println("Error inserting minigame session data: " + e.getMessage());
        }

    }

    @Override
    void lose() {
        if (gameEnded) return; // Prevent multiple triggers
        gameEnded = true;
        try {
            System.out.println("currentLives: " + GameState.currentLives);
            DatabaseHandler.insertMinigameSessionData(5, false);
            GameState.currentLives--; // Kurangi nyawa pemain
            GameState.currentScore += score; // Tambahkan skor ke total skor
        } catch (SQLException e) {
            System.err.println("Error inserting minigame session data: " + e.getMessage());
        }
    }
}
