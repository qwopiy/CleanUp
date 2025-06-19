package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CleanUpController {

    @FXML
    public ImageView startButton;
    @FXML
    public Label play;

    @FXML
    public ImageView settingButton;
    @FXML
    public ImageView achievementButton;

    @FXML
    public void onStartButtonClick(javafx.scene.input.MouseEvent event) {
        try {
            AudioController.click();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition-screen.fxml"));
            System.out.println(loader.getLocation());
            Parent root = loader.load();

            // Get the controller and call init()
            GameState.currentScore = 0;
            GameState.currentLives = 3;
            TransitionScreenController controller = loader.getController();
            controller.show();
            DatabaseHandler.initSession();

            // Set the new root for the current scene
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onStartButtonHover() {
        startButton.setFitWidth(startButton.getFitWidth() + 20);
        startButton.setFitHeight(startButton.getFitHeight() + 20);

        startButton.setX(startButton.getX() - 10);
        startButton.setY(startButton.getY() - 10);

        play.setLayoutX(play.getLayoutX() + 10);
        play.setLayoutY(play.getLayoutY() + 10);
    }

    public void onStartButtonExit() {
        startButton.setFitWidth(startButton.getFitWidth() - 20);
        startButton.setFitHeight(startButton.getFitHeight() - 20);

        startButton.setX(startButton.getX() + 10);
        startButton.setY(startButton.getY() + 10);

        play.setLayoutX(play.getLayoutX() - 10);
        play.setLayoutY(play.getLayoutY() - 10);
    }

    public void onSettingButtonClick(javafx.scene.input.MouseEvent event) {
        try {
            AudioController.click();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onSettingButtonHover() {
        settingButton.setFitWidth(settingButton.getFitWidth() + 20);
        settingButton.setFitHeight(settingButton.getFitHeight() + 20);

        settingButton.setX(settingButton.getX() - 10);
        settingButton.setY(settingButton.getY() - 10);
    }

    public void onSettingButtonExit() {
        settingButton.setFitWidth(settingButton.getFitWidth() - 20);
        settingButton.setFitHeight(settingButton.getFitHeight() - 20);

        settingButton.setX(settingButton.getX() + 10);
        settingButton.setY(settingButton.getY() + 10);
    }

    public void onAchievementButtonClick(javafx.scene.input.MouseEvent event) {
        try {
            AudioController.click();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("achievement.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onAchievementButtonHover() {
        achievementButton.setFitWidth(achievementButton.getFitWidth() + 20);
        achievementButton.setFitHeight(achievementButton.getFitHeight() + 20);

        achievementButton.setX(achievementButton.getX() - 10);
        achievementButton.setY(achievementButton.getY() - 10);
    }

    public void onAchievementButtonExit() {
        achievementButton.setFitWidth(achievementButton.getFitWidth() - 20);
        achievementButton.setFitHeight(achievementButton.getFitHeight() - 20);

        achievementButton.setX(achievementButton.getX() + 10);
        achievementButton.setY(achievementButton.getY() + 10);
    }
}