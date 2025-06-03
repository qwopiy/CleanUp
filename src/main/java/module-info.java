module com.wi3uplus2.cleanup {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.wi3uplus2.cleanup;
    opens com.wi3uplus2.cleanup to javafx.fxml;
}