module com.wi3uplus2.cleanup {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.wi3uplus2.cleanup to javafx.fxml;
    exports com.wi3uplus2.cleanup;
}