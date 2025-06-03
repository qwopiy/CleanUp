module com.example.cleanup_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cleanup_javafx to javafx.fxml;
    exports com.example.cleanup_javafx;
    exports com.wi3uplus2.cleanup;
    opens com.wi3uplus2.cleanup to javafx.fxml;
}