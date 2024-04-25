package org.example.workshop6javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("chart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

//        // chart controller
        ChartController controller = fxmlLoader.getController();
        controller.initializeToggle();

        stage.setTitle("Travel Experts Data Manager");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}