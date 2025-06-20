package com.wi3uplus2.cleanup;

import javafx.scene.image.Image;

public class Trash {
    String type;
    Image image;
    double x, y;
    double offsetX, offsetY;
    boolean dragging = false;
    String userData;

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getUserData() {
        return userData;
    }

    public Trash(String type,Image image, double x, double y) {
        this.type = type;
        this.image = image;
        this.x = x;
        this.y = y;
    }
}