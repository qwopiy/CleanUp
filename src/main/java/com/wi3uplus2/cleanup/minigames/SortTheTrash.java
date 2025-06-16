package com.wi3uplus2.cleanup.minigames;

import com.wi3uplus2.cleanup.DatabaseHandler;
import com.wi3uplus2.cleanup.GameState;
import com.wi3uplus2.cleanup.TransitionScreenController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.sql.SQLException;
import java.util.Random;

public class SortTheTrash extends Game {

    // TODO: logika cuma ada beberapa trash dalam satu game, misalnya 5 trash setelah habis langsung ke transition screen
    @FXML
    private Canvas canvas;
    @FXML
    private Label countdownLabel;

    private int countdownSeconds = 10; // Set countdown duration
    private Timeline countdownTimeline;

    final int WIDTH = 800;
    final int HEIGHT = 600;

    @Override
    void win() throws SQLException {
        GameState.currentScore += score;
        DatabaseHandler.insertMinigameSessionData(2, true);
    }

    @Override
    void lose() throws SQLException {
        GameState.currentLives--;
        DatabaseHandler.insertMinigameSessionData(2, false);
    }

    class Trash {
        String type;
        double x, y;
        double offsetX, offsetY;
        boolean dragging = false;

        public Trash(String type, double x, double y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }
    }

    Trash currentTrash;
    Random rand = new Random();

    int score = 0;
    String[] types = {"plastic", "paper", "metal"};

    // Tong sampah (x, lebar, label, warna)
    double[][] bins = {
            {100, 150, 0}, // plastik
            {325, 150, 1}, // kertas
            {550, 150, 2}  // metal
    };

    @FXML
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        newTrash();

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
            lose();
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
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Gambar tong sampah
        for (int i = 0; i < bins.length; i++) {
            double x = bins[i][0];
            double width = bins[i][1];
            int typeIdx = (int) bins[i][2];
            String label = types[typeIdx];

            gc.setFill(Color.GRAY);
            gc.fillRect(x, HEIGHT - 100, width, 80);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(20));
            gc.fillText(label.toUpperCase(), x + 30, HEIGHT - 50);
        }

        // Gambar sampah
        if (currentTrash != null) {
            gc.setFill(getColor(currentTrash.type));
            gc.fillOval(currentTrash.x, currentTrash.y, 60, 60);

            gc.setFill(Color.BLACK);
            gc.setFont(Font.font(14));
            gc.fillText(currentTrash.type, currentTrash.x + 5, currentTrash.y + 35);
        }

        // Score
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(24));
        gc.fillText("Score: " + score, 20, 30);
    }

    void onMousePressed(MouseEvent e) {
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

                if (currentTrash.x + 30 >= x && currentTrash.x + 30 <= x + width &&
                        currentTrash.y + 30 >= HEIGHT - 100) {
                    if (type.equals(currentTrash.type)) {
                        score += 10;
                        newTrash();
                        return;
                    } else {
                        score -= 5;
                        newTrash();
                        return;
                    }
                }
            }

            // Kalau nggak kena tong manapun, reset posisi
            currentTrash.x = WIDTH / 2 - 30;
            currentTrash.y = HEIGHT / 2 - 30;
        }
    }

    void newTrash() {
        String type = types[rand.nextInt(types.length)];
        currentTrash = new Trash(type, WIDTH / 2 - 30, HEIGHT / 2 - 30);
    }

    Color getColor(String type) {
        return switch (type) {
            case "plastic" -> Color.LIGHTBLUE;
            case "paper" -> Color.BURLYWOOD;
            case "metal" -> Color.SILVER;
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