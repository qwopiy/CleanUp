package com.wi3uplus2.cleanup;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TrashBalancing extends Application {

    private double angle = 0;
    private double angularVelocity = 0;

    private final double gravity = 18;
    private final double tapForce = 4.5;
    private final double maxAngle = 35;

    private Rectangle tong;
    private Circle isiSampah;
    private Group tongGroup;

    private boolean gameOver = false;
    private long lastTime = 0;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        tong = new Rectangle(100, 100, Color.DARKGRAY);
        tong.setArcWidth(10);
        tong.setArcHeight(10);

        isiSampah = new Circle(20, Color.ORANGE);
        isiSampah.setTranslateX(50);
        isiSampah.setTranslateY(30);

        tongGroup = new Group(tong, isiSampah);
        tongGroup.setTranslateX(300);
        tongGroup.setTranslateY(200);
        root.getChildren().add(tongGroup);

        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.setTitle("Trash Balancing");
        stage.show();

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

        AnimationTimer timer = new AnimationTimer() {
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

    private void triggerTumpah() {
        gameOver = true;
        System.out.println("Sampah Tumpah!");

        double arah = Math.signum(angle);

        TranslateTransition jatuh = new TranslateTransition(Duration.seconds(1), isiSampah);
        jatuh.setByX(arah * 200);
        jatuh.setByY(150);
        jatuh.setInterpolator(Interpolator.EASE_OUT);

        RotateTransition muter = new RotateTransition(Duration.seconds(1), isiSampah);
        muter.setByAngle(arah * 720);

        new ParallelTransition(jatuh, muter).play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
