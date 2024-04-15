module org.example.workshop6javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.workshop6javafx to javafx.fxml;
    exports org.example.workshop6javafx;
}