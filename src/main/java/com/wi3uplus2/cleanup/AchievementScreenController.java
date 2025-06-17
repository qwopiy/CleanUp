package com.wi3uplus2.cleanup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

public class AchievementScreenController {
    @FXML
    private Label Highscore;

    @FXML
    private VBox contentList;


    @FXML
    void Kembali(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initialize() {


        for (int i = 1; i < 10; i++) {
            HBox baris = new HBox(50);
            Image image = new Image(getClass().getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_poison.png"));

            ImageView img = new ImageView(image);
            img.setFitWidth(100);
            img.setFitHeight(100);

            VBox labelBox = new VBox();
            labelBox.getChildren().addAll(new Label("Game ke-" + i), new Label("Jumlah Mati:"));

            baris.getChildren().addAll(img, labelBox);
            contentList.getChildren().add(baris);
        }
    }

}
