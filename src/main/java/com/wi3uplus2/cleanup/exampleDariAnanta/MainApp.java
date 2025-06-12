package com.wi3uplus2.cleanup.exampleDariAnanta;
//import com.example.javafxlearn.Window1;
//import com.example.javafxlearn.Window2;
//import com.example.javafxlearn.Window3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showMainMenu();
    }

    public void showMainMenu() {
        Button btn1 = new Button("Ke Halaman 1");
        btn1.setOnAction(e -> new Window1().tampilkan(primaryStage, this));

        Button btn2 = new Button("Ke Halaman 2");
        btn2.setOnAction(e -> new Window2().tampilkan(primaryStage, this));

        Button btn3 = new Button("Ke Halaman 3");
        btn3.setOnAction(e -> new Window3().tampilkan(primaryStage, this));

        VBox vbox = new VBox(20, btn1, btn2, btn3);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setTitle("Menu Utama");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

