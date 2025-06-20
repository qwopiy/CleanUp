package com.wi3uplus2.cleanup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EncyclopediaController {

    @FXML
    private ImageView back;

    @FXML
    private ImageView next;

    @FXML
    private ImageView prev;

    @FXML
    private Label title;

    @FXML
    private Label body;

    @FXML
    private ImageView contentImage;

    private int currentPage = 0;

    public void initialize() {
        title.setText(EncyclopediaContent.TITLES[currentPage]);
        body.setText(EncyclopediaContent.BODIES[currentPage]);

        Image image = new Image(getClass().getResourceAsStream(EncyclopediaContent.IMAGES[currentPage]));
        contentImage.setImage(image);
    }

    public void onBackButtonClick(javafx.scene.input.MouseEvent event) {
        try {
            AudioController.click();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onNextButtonClick(javafx.scene.input.MouseEvent event) {
        AudioController.click();
        if(currentPage >= EncyclopediaContent.TITLES.length - 1) {
            return;
        }

        currentPage++;
        title.setText(EncyclopediaContent.TITLES[currentPage]);
        body.setText(EncyclopediaContent.BODIES[currentPage]);

        Image image = new Image(getClass().getResourceAsStream(EncyclopediaContent.IMAGES[currentPage]));
        contentImage.setImage(image);
    }

    public void onPrevButtonClick(javafx.scene.input.MouseEvent event) {
        AudioController.click();
        if(currentPage == 0) {
            return;
        }

        currentPage--;
        title.setText(EncyclopediaContent.TITLES[currentPage]);
        body.setText(EncyclopediaContent.BODIES[currentPage]);

        Image image = new Image(getClass().getResourceAsStream(EncyclopediaContent.IMAGES[currentPage]));
        contentImage.setImage(image);
    }

}
