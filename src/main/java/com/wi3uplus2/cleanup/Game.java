package com.wi3uplus2.cleanup;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.SQLException;

abstract class Game {
    abstract void win() throws SQLException;
    abstract void lose() throws SQLException;

    protected Timeline countdownTimeline;
    protected int seconds = 10;

    public void startCountdown(Label label) {
        label.setText("Time: " + seconds);
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds--;
                    label.setText("Time: " + seconds);
                    if (seconds <= 0) {
                        countdownTimeline.stop();
                        onCountdownEnd(label);
                    }
                })
        );
        countdownTimeline.setCycleCount(seconds);
        countdownTimeline.play();
    }

    public void onCountdownEnd(Label label) {
        // Switch to transition screen or show game over
        try {
            lose();
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

        // Delta Time
    private long lastTime = 0;
    private void startDeltaTime() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime > 0) {
                    double deltaTime = (now - lastTime) / 1_000_000_000.0; // seconds
                    onUpdate(deltaTime);
                    if (!GameState.inGame) {
                        stop();
                    }
                }
                lastTime = now;
            }
        };
        timer.start();  
    }

    private void onUpdate(double deltaTime) {
        // deltaTime is in seconds (e.g., 0.016 for ~60fps)
        System.out.println("Delta Time: " + deltaTime);

        // Example usage:
        // position += speed * deltaTime;
    }
}
