module org.example.workshop6javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.gluonhq.charm.glisten;


    opens org.example.workshop6javafx to javafx.fxml;
    exports org.example.workshop6javafx;
}