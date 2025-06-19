package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class SettingController {
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
        bgmControl.setValue(AudioController.bgmVolume * 100);
        sfxControl.setValue(AudioController.sfxVolume * 100);
    }

    public void onClickExit() {
        try {
            AudioController.click();
            Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
            Scene scene = bgmControl.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

    public void sliderBGM() throws SQLException {
        System.out.println(bgmControl.getValue());
        AudioController.bgmVolume = bgmControl.getValue() / 100.0;
        AudioController.musicPlayer.setVolume(bgmControl.getValue() / 100.0);
        DatabaseHandler.setVolume("bgm", bgmControl.getValue() / 100.0);
    }

    public void sliderSFX() throws SQLException {
        System.out.println(sfxControl.getValue());
        AudioController.sfxVolume = sfxControl.getValue() / 100.0;
        DatabaseHandler.setVolume("sfx", bgmControl.getValue() / 100.0);
        AudioController.click();
    }
}
