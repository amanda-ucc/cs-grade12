module com.amanda {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.amanda to javafx.fxml;
    exports com.amanda;
}
