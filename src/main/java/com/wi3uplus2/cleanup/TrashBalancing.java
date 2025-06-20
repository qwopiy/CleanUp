package com.example.tess;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrashBalancing extends Application {

    private final Random rand = new Random();

    private double angle = 0;
    private double angularVelocity = 0;

    private final double gravity = 18;
    private final double tapForce = 4.5;
    private final double maxAngle = 35;

    private ImageView tong;
    private Group tongGroup;
    private boolean gameOver = false;
    private long lastTime = 0;
    private AnimationTimer timer;

    private final String[] sampahFiles = {
            "/assets/anorganik_plasticBag.png",
            "/assets/anorganik_plasticBottle.png",
            "/assets/anorganik_snackPack.png",
            "/assets/anorganik_sodaCan.png"
    };

    private final List<ImageView> sampahImagesList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        Image tongImg = new Image(getClass().getResource("/assets/handwithTrashCan.png").toExternalForm());
        tong = new ImageView(tongImg);
        tong.setFitWidth(150);
        tong.setFitHeight(200);

        tongGroup = new Group(tong);
        tongGroup.setTranslateX(350);
        tongGroup.setTranslateY(300);
        root.getChildren().add(tongGroup);

        Rectangle ground = new Rectangle(0, 550, 800, 50);
        ground.setFill(Color.TRANSPARENT);
        root.getChildren().add(ground);

        Button restartBtn = new Button("Restart");
        restartBtn.setLayoutX(600);
        restartBtn.setLayoutY(20);
        restartBtn.setOnAction(e -> restartGame());
        root.getChildren().add(restartBtn);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Trash Balancing");
        stage.setResizable(true);
        stage.show();

        scene.widthProperty().addListener((obs, oldVal, newVal) -> centerTong(scene));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> centerTong(scene));
        centerTong(scene);

        angle = 1;

        scene.setOnKeyPressed(e -> {
            if (gameOver) return;

            if (e.getCode() == KeyCode.D && angle < 0) {
                angularVelocity += tapForce;
            }
            if (e.getCode() == KeyCode.A && angle > 0) {
                angularVelocity -= tapForce;
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) return;

                if (lastTime > 0) {
                    double delta = (now - lastTime) / 1_000_000_000.0;
                    angularVelocity += Math.signum(angle) * gravity * delta;
                    angle += angularVelocity * delta;
                    tongGroup.setRotate(angle);

                    if (Math.abs(angle) > maxAngle) {
                        triggerTumpah();
                        stop();
                    }
                }
                lastTime = now;
            }
        };
        timer.start();
    }

    private void centerTong(Scene scene) {
        tongGroup.setTranslateX(scene.getWidth() / 2);
        tongGroup.setTranslateY(scene.getHeight() / 2);
    }

    private void triggerTumpah() {
        gameOver = true;
        double arah = Math.signum(angle);
        System.out.println("Sampah Tumpah!");

        double startX = tongGroup.getTranslateX();
        double startY = tongGroup.getTranslateY();

        for (int i = 0; i < 15; i++) {
            String file = sampahFiles[rand.nextInt(sampahFiles.length)];
            Image img = new Image(getClass().getResource(file).toExternalForm());
            ImageView sampah = new ImageView(img);

            double size = 25 + rand.nextDouble() * 15;
            sampah.setFitWidth(size);
            sampah.setFitHeight(size);
            sampah.setPreserveRatio(true);

            sampah.setTranslateX(startX + tong.getFitWidth() / 2 - size / 2);
            sampah.setTranslateY(startY - 20);

            ((Pane) tongGroup.getParent()).getChildren().add(sampah);
            sampahImagesList.add(sampah);

            TranslateTransition jatuh = new TranslateTransition(Duration.seconds(1.5), sampah);
            jatuh.setByX(arah * (100 + rand.nextDouble() * 200));
            jatuh.setByY(250 + rand.nextDouble() * 50);
            jatuh.setInterpolator(Interpolator.EASE_OUT);

            RotateTransition muter = new RotateTransition(Duration.seconds(1.5), sampah);
            muter.setByAngle((arah * 360) + (rand.nextDouble() * 180));

            new ParallelTransition(jatuh, muter).play();
        }
    }


    private void restartGame() {
        angle = 1;
        angularVelocity = 0;
        gameOver = false;
        tongGroup.setRotate(0);
        lastTime = 0;

        Pane root = (Pane) tongGroup.getParent();
        root.getChildren().removeAll(sampahImagesList);
        sampahImagesList.clear();

        timer.start();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
