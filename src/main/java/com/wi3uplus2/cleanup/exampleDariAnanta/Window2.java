package com.wi3uplus2.cleanup.exampleDariAnanta;// Halaman2.java

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window2 {
    public void tampilkan(Stage stage, MainApp app) {
        Label label = new Label("Ini Halaman 2");
        Button kembali = new Button("Kembali ke Menu");
        kembali.setOnAction(e -> app.showMainMenu());

        VBox layout = new VBox(20, label, kembali);
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
    }
}
