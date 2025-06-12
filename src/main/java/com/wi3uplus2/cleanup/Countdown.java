package com.wi3uplus2.cleanup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Countdown {

//    private Timeline countdownTimeline;
//    private int seconds = 10;
//
//    public void startCountdown(Label label) {
//        label.setText("Time: " + seconds);
//        countdownTimeline = new Timeline(
//                new KeyFrame(Duration.seconds(1), event -> {
//                    seconds--;
//                    label.setText("Time: " + seconds);
//                    if (seconds <= 0) {
//                        countdownTimeline.stop();
//                        onCountdownEnd(label);
//                    }
//                })
//        );
//        countdownTimeline.setCycleCount(seconds);
//        countdownTimeline.play();
//    }
//
//    private void onCountdownEnd(Label label) {
//        GameState.currentLives--;
//        // Switch to transition screen or show game over
//        try {
//            DatabaseHandler.insertMinigameSessionData(1, false);
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
//            Parent root = loader.load();
//            TransitionScreenController controller = loader.getController();
//            controller.show();
//            Scene scene = label.getScene();
//            scene.setRoot(root);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
