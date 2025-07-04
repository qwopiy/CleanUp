package com.wi3uplus2.cleanup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CleanUpApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CleanUpApp.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Clean Up Game");
        stage.setScene(scene);
        stage.setResizable(false);
//        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            DatabaseHandler.connect();
            AssetLoader.init();
            AudioController.startBGM();
        } catch (Exception e) {
            System.out.println(e);
        }
        launch(args);
    }
}
