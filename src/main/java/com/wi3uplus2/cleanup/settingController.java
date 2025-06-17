package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;

public class settingController {
    @FXML
    private ImageView exit;

    @FXML
    private ImageView bgmControlImage;
    @FXML
    private Slider bgmControl;

    @FXML
    private ImageView sfxControlImage;
    @FXML
    private Slider sfxControl;

    public void initialize() {
        bgmControl.setValue(AudioController.musicPlayer.getVolume() * 100);
        sfxControl.setValue(AudioController.boomPlayer.getVolume() * 100);
    }

    public void onClickExit() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
            Scene scene = bgmControl.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

    public void sliderBGM() {
        System.out.println(bgmControl.getValue());
        AudioController.musicPlayer.setVolume(bgmControl.getValue() / 100.0);
    }

    public void sliderSFX() {
        System.out.println(sfxControl.getValue());
        AudioController.boomPlayer.setVolume(sfxControl.getValue() / 100.0);
    }
}
