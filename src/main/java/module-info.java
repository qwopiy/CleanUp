module com.wi3uplus2.cleanup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.wi3uplus2.cleanup to javafx.fxml;
    exports com.wi3uplus2.cleanup;
}