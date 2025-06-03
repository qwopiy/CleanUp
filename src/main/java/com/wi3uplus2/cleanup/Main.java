package com.wi3uplus2.cleanup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    final int WIDTH = 800;
    final int HEIGHT = 600;

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

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        newTrash();

        canvas.setOnMousePressed(e -> onMousePressed(e));
        canvas.setOnMouseDragged(e -> onMouseDragged(e));
        canvas.setOnMouseReleased(e -> onMouseReleased(e));

        new AnimationTimerExt(60, () -> draw(gc)).start();

        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.setTitle("Game Pilah Sampah (Drag & Drop)");
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
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