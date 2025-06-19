package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainMenuController {

    @FXML
    private Button btnMain;

    @FXML
    private Button btnTrophy;

    @FXML
    private Button btnEncyclopedia;

    @FXML
    private Button btnSetting;

    @FXML
    private ImageView trophyImage, encyclopediaImage, settingImage;

    @FXML
    public void initialize() {
        // Logika awal saat tampilan dimuat
        System.out.println("Main Menu loaded!");

        // Contoh aksi klik tombol
        btnMain.setOnAction(e -> System.out.println("Mulai game..."));
        btnTrophy.setOnAction(e -> System.out.println("Buka Trofi"));
        btnEncyclopedia.setOnAction(e -> System.out.println("Buka Ensiklopedia"));
        btnSetting.setOnAction(e -> System.out.println("Buka Setting"));
    }
}
