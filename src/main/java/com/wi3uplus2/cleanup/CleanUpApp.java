package com.wi3uplus2.cleanup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CleanUpApp extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CleanUpApp.class.getResource("/com/wi3uplus2/cleanup/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Clean Up Game");
        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        DatabaseHandler.connect();
        launch(args);
    }
}
