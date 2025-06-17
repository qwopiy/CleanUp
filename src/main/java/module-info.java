module com.wi3uplus2.cleanup {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;
    requires javafx.media;

    opens com.wi3uplus2.cleanup to javafx.fxml;
    exports com.wi3uplus2.cleanup;
}