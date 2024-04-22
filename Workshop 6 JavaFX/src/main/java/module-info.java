module org.example.workshop6javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens org.example.workshop6javafx to javafx.fxml;
    exports org.example.workshop6javafx;
}