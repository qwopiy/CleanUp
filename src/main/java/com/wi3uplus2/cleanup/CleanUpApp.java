package com.wi3uplus2.cleanup;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CleanUpApp extends Application {

//    private long lastTime = 0;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CleanUpApp.class.getResource("/com/wi3uplus2/cleanup/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Clean Up Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.show();

        GameState.currentScore = 0;
        GameState.currentLives = 3;

//        // Delta Time
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                if (lastTime > 0) {
//                    double deltaTime = (now - lastTime) / 1_000_000_000.0; // seconds
//                    onUpdate(deltaTime);
//                }
//                lastTime = now;
//            }
//        };
//        timer.start();
    }

//    private void onUpdate(double deltaTime) {
//        // deltaTime is in seconds (e.g., 0.016 for ~60fps)
//        System.out.println("Delta Time: " + deltaTime);
//
//        // Example usage:
//        // position += speed * deltaTime;
//    }

    public static void main(String[] args) {
        DatabaseHandler.connect();
        launch(args);
    }
}
