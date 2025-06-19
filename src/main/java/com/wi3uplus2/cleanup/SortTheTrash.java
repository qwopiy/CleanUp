package com.wi3uplus2.cleanup;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.sql.SQLException;
import java.util.Random;

public class SortTheTrash extends Game {

    @FXML
    private Canvas canvas;
    double canvasX;
    double canvasY;
    @FXML
    private Label countdownLabel;

    private int trashLimit = 5;
    private int currentTrashCount = 0;
    private int countdownSeconds = 10; // Set countdown duration
    private Timeline countdownTimeline;

    final int WIDTH = 1280;
    final int HEIGHT = 720;

    @Override
    void win() throws SQLException {
        GameState.currentScore += score;
        DatabaseHandler.insertMinigameSessionData(2, true);
    }

    @Override
    void lose() throws SQLException {
        GameState.currentLives--;
        GameState.currentScore += score;
        DatabaseHandler.insertMinigameSessionData(2, false);
    }

    class Trash {
        String type;
        Image image;
        double x, y;
        double offsetX, offsetY;
        boolean dragging = false;

        public Trash(String type,Image image, double x, double y) {
            this.type = type;
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }

    Trash currentTrash;
    Random rand = new Random();

    int score = 0;
    String[] types = {"anorganik", "organik", "B3"};

    // Tong sampah (x, lebar, label, warna)
    double[][] bins = {
            {WIDTH / 3 - 150, 150, 0}, // anorganik
            {WIDTH / 2, 150, 1}, // organik
            {WIDTH * 2 / 3 + 150, 150, 2}  // B3
    };
    private Image[] binImage = {
            AssetLoader.inorganicTrashBin,
            AssetLoader.organicTrashBin,
            AssetLoader.B3TrashBin
    };

    // Gambar sampah yang bisa diambil
    private Image[] trashImages = {
            AssetLoader.inorganicTrash[0], // plasticBag
            AssetLoader.inorganicTrash[1], // plasticBottle
            AssetLoader.inorganicTrash[2], // snackPack
            AssetLoader.inorganicTrash[3], // sodaCan
            AssetLoader.organicTrash[0],   // apple
            AssetLoader.organicTrash[1],   // banana
            AssetLoader.organicTrash[2],   // dryLeaf
            AssetLoader.organicTrash[3],   // stick
            AssetLoader.B3Trash[0],        // battery
            AssetLoader.B3Trash[1],        // brokenGlass
            AssetLoader.B3Trash[2],        // brokenLamp
            AssetLoader.B3Trash[3]         // poison
    };

    @FXML
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        newTrash();

        canvasX = canvas.getWidth();
        canvasY = canvas.getHeight();

        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseDragged(this::onMouseDragged);
        canvas.setOnMouseReleased(this::onMouseReleased);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc);
            }
        }.start();
    }

    public void startCountdown() {
        countdownLabel.setText("Time: " + countdownSeconds);
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    countdownSeconds--;
                    countdownLabel.setText("Time: " + countdownSeconds);
                    if (countdownSeconds <= 0) {
                        countdownTimeline.stop();
                        onCountdownEnd();
                    }
                })
        );
        countdownTimeline.setCycleCount(countdownSeconds);
        countdownTimeline.play();
    }

    private void onCountdownEnd() {
        // Switch to transition screen or show game over
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            Parent root = loader.load();
            TransitionScreenController controller = loader.getController();
            controller.show();
            Scene scene = countdownLabel.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void draw(GraphicsContext gc) {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, canvasX, canvasY);

        // Gambar tong sampah
        for (int i = 0; i < bins.length; i++) {
            double x = bins[i][0];
            double width = bins[i][1];
            int typeIdx = (int) bins[i][2];
            String label = types[typeIdx];

            gc.drawImage(binImage[i],x - 100, canvasY - 200, width, 150);

            gc.setFill(Color.BLACK);
//            gc.fillRect(x - 100, canvasY - 200, width, 150);
            gc.setFont(Font.font(18));
            gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
            gc.fillText(label.toUpperCase(), x - 25, canvasY - 127);
        }

        // Gambar sampah
        if (currentTrash != null) {
            gc.drawImage(currentTrash.image, currentTrash.x - 30, currentTrash.y - 30, 100, 100);
        }

        // Score
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(24));
        gc.fillText("Score: " + score, 50, 30);
        gc.fillText("Trash Count: " + currentTrashCount + "/" + trashLimit, 50, 60);
    }

    void onMousePressed(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        if (currentTrash != null &&
                e.getX() >= currentTrash.x && e.getX() <= currentTrash.x + 60 &&
                e.getY() >= currentTrash.y && e.getY() <= currentTrash.y + 60) {
            currentTrash.dragging = true;
            currentTrash.offsetX = e.getX() - currentTrash.x;
            currentTrash.offsetY = e.getY() - currentTrash.y;
        }
    }

    void onMouseDragged(MouseEvent e) {
        if (currentTrash != null && currentTrash.dragging) {
            currentTrash.x = e.getX() - currentTrash.offsetX;
            currentTrash.y = e.getY() - currentTrash.offsetY;
        }
    }

    void onMouseReleased(MouseEvent e) {
        if (currentTrash != null && currentTrash.dragging) {
            currentTrash.dragging = false;

            for (double[] bin : bins) {
                double x = bin[0];
                double width = bin[1];
                int idx = (int) bin[2];
                String type = types[idx];

                if (isTrashIntersectingBin(currentTrash, bin)) {
                    if (type.equals(currentTrash.type)) {
                        score += 5;
                    } else {
                        score -= 10;
                    }
                    currentTrashCount++;
                    newTrash();
                    return;
                }
            }

            // Kalau nggak kena tong manapun, reset posisi
            currentTrash.x = WIDTH / 2 - 30;
            currentTrash.y = HEIGHT / 2 - 100;
        }
    }

    private boolean isTrashIntersectingBin(Trash trash, double[] bins) {
        AudioController.vineBoom();
        double binX = bins[0];
        double binWidth = bins[1];
        double binY = canvasY - 200; // Y position of the bins
        double binHeight = 150; // Height of the bins

        double trashRight = trash.x + 60; // Width of trash
        double trashBottom = trash.y + 60; // Height of trash

        if (trashRight >= binX &&
            trash.x <= binX + binWidth &&
            trashBottom >= binY &&
            trash.y <= binY + binHeight) {
            return true;
        }
        return false;
    }

    void newTrash() {
        if (currentTrashCount >= trashLimit) {
            try {
                if(score >= 0) {
                    win();
                } else {
                    lose();
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
                Parent root = loader.load();
                TransitionScreenController controller = loader.getController();
                controller.show();
                Scene scene = canvas.getScene();
                scene.setRoot(root);
            } catch (Exception e) {
                System.out.println(e);
            }
            return;
        }
        String type = types[rand.nextInt(types.length)];
        int imageIndex = switch (type) {
            case "anorganik" -> rand.nextInt(4); // 0-3
            case "organik" -> 4 + rand.nextInt(4); // 4-7
            case "B3" -> 8 + rand.nextInt(4); // 8-11
            default -> 0; // fallback
        };

        Image image = trashImages[imageIndex];
        currentTrash = new Trash(type, image, WIDTH / 2 - 30, HEIGHT / 2 - 100);
    }

    Color getColor(String type) {
        return switch (type) {
            case "anorganik" -> Color.LIGHTBLUE;
            case "organik" -> Color.BURLYWOOD;
            case "B3" -> Color.SILVER;
            default -> Color.BLACK;
        };
    }

    // Kelas helper AnimationTimer dengan FPS fix
    class AnimationTimerExt extends javafx.animation.AnimationTimer {
        private long interval;
        private long last = 0;
        private Runnable onTick;

        public AnimationTimerExt(int fps, Runnable onTick) {
            this.interval = 1_000_000_000 / fps;
            this.onTick = onTick;
        }

        @Override
        public void handle(long now) {
            if (now - last > interval) {
                last = now;
                onTick.run();
            }
        }
    }
}