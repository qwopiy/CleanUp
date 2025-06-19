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
import java.util.*;

public class Game3Controller {

    @FXML private AnchorPane rootPane;
    @FXML private ProgressBar progressBar;
    @FXML private Label countdownLabel;
    @FXML private Label scoreLabel;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem pauseMenu, replayMenu, exitMenu;
    @FXML private Button btnOrganik, btnAnorganik, btnB3;
    @FXML private Label finishedLabel;
    @FXML private Pane lane1, lane2, lane3, lane4;
    @FXML private AnchorPane pauseOverlay;

    private int score = 0;
    private boolean gameStarted = false;
    private boolean isPaused = false;
    private Timeline progressTimeline;
    private final Random random = new Random();
    private final Pane[] lanes = new Pane[4];
    private final List<Timeline> trashTimelines = new ArrayList<>();
    private Timeline spawnTimeline;

    @FXML
    public void initialize() {
        lanes[0] = lane1;
        lanes[1] = lane2;
        lanes[2] = lane3;
        lanes[3] = lane4;

        updateScoreLabel();

        pauseMenu.setOnAction(e -> pauseGame());
        replayMenu.setOnAction(e -> restartGame());
        exitMenu.setOnAction(e -> exitGame());

        setupDropHandlers();

        disableButtons(true);
        startCountdown();
        finishedLabel.setVisible(false);
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

    public void startCountdown() {
        countdownLabel.setText("3");

        Timeline countdown = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> countdownLabel.setText("2")),
                new KeyFrame(Duration.seconds(2), e -> countdownLabel.setText("1")),
                new KeyFrame(Duration.seconds(3), e -> countdownLabel.setText("Mulai!")),
                new KeyFrame(Duration.seconds(4), e -> {
                    countdownLabel.setText("");
                    startGame();
                })
        );
        countdown.play();
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
        spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> spawnTrash()));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();

        progressTimeline.setOnFinished(ev -> {
            spawnTimeline.stop();
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

        Timeline move = new Timeline(new KeyFrame(Duration.millis(30), ev -> {
            if (!isPaused && "alive".equals(trash.getUserData())) {
                trash.setLayoutX(trash.getLayoutX() + 2);
                if (trash.getLayoutX() > rootPane.getWidth()) {
                    lane.getChildren().remove(trash);
                    endLevelWithMiss();
                }
            }
        }));
        move.setCycleCount(Animation.INDEFINITE);
        move.play();
        trashTimelines.add(move);

        trash.setOnDragDetected(e -> {
            if (!"handled".equals(trash.getUserData())) {
                Dragboard db = trash.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(name);
                db.setContent(content);
                e.consume();
            }
        });

        trash.setOnMouseDragged(event -> {
            if (!"handled".equals(trash.getUserData())) {
                trash.setLayoutX(event.getSceneX() - 40);
                trash.setLayoutY(event.getSceneY() - 40);
                event.consume();
            }
        });
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor: " + score);
    }

    private void setupProgressBar() {
        if (progressTimeline != null) progressTimeline.stop();

        progressBar.setProgress(0.0);
        KeyValue keyValue = new KeyValue(progressBar.progressProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(60), keyValue);

        progressTimeline = new Timeline(keyFrame);
        progressTimeline.play();
    }

    private void endLevel() {
        disableButtons(true);
        gameStarted = false;
        finishedLabel.setVisible(true);
        clearAllTrash();

        Timeline hideLabel = new Timeline(new KeyFrame(Duration.seconds(5), e -> finishedLabel.setVisible(false)));
        hideLabel.play();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waktu Habis");
        alert.setHeaderText(null);
        alert.setContentText("Waktu habis!\nSkor akhir kamu: " + score);
        alert.showAndWait();

        restartGame();
    }

    private void endLevelWithMiss() {
        if (gameStarted) {
            disableButtons(true);
            gameStarted = false;
            clearAllTrash();
            spawnTimeline.stop();
            if (progressTimeline != null) progressTimeline.stop();

            countdownLabel.setText("Game Over");
            countdownLabel.setVisible(true);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Sampah terlewat!\nSkor akhir kamu: " + score);
            alert.showAndWait();

            restartGame();
        }
    }

    private void pauseGame() {
        isPaused = true;
        if (progressTimeline != null) progressTimeline.pause();
        if (spawnTimeline != null) spawnTimeline.pause();
        trashTimelines.forEach(Timeline::pause);

        pauseOverlay.setVisible(true); // tampilkan overlay custom
    }

    @FXML
    private void resumeGame() {
        pauseOverlay.setVisible(false);
        isPaused = false;
        if (progressTimeline != null) progressTimeline.play();
        if (spawnTimeline != null) spawnTimeline.play();
        trashTimelines.forEach(Timeline::play);
    }

    private void restartGame() {
        if (progressTimeline != null) progressTimeline.stop();
        if (spawnTimeline != null) spawnTimeline.stop();
        trashTimelines.forEach(Timeline::stop);
        trashTimelines.clear();
        clearAllTrash();
        score = 0;
        updateScoreLabel();
        countdownLabel.setText("");
        startCountdown();
    }

    private void clearAllTrash() {
        for (Pane lane : lanes) {
            lane.getChildren().clear();
        }
    }

    private void exitGame() {
        try {
            if (progressTimeline != null) progressTimeline.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
            Parent root = loader.load();
            Scene scene = menuButton.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println("Gagal keluar ke menu utama: " + e.getMessage());
        }
    }

    private void disableButtons(boolean disable) {
        btnOrganik.setDisable(disable);
        btnAnorganik.setDisable(disable);
        btnB3.setDisable(disable);
    }
}
