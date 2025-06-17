package com.wi3uplus2.cleanup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AchievementController {
    public void Kembali(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @FXML
//    VBox itemList; // VBox di dalam ScrollPane
//
//    public void initialize() {
//        for (int i = 1; i < 10; i++) {
//            HBox baris = new HBox(50);
//            ImageView img = new ImageView(new Image("file:..."));
//            img.setFitWidth(100);
//            img.setFitHeight(100);
//
//            VBox labelBox = new VBox();
//            labelBox.getChildren().addAll(new Label("Game ke-" + i), new Label("Jumlah Mati:"));
//
//            baris.getChildren().addAll(img, labelBox);
//            itemList.getChildren().add(baris);
//        }
//    }
}
