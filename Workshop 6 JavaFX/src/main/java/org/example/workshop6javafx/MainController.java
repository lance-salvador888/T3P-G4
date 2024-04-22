/**
 * Sample Skeleton for 'main-view.fxml' Controller Class
 */

package org.example.workshop6javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class MainController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="lcTravelerGraph"
    private LineChart<?, ?> lcTravelerGraph; // Value injected by FXMLLoader

    @FXML // fx:id="lcXDate"
    private CategoryAxis lcXDate; // Value injected by FXMLLoader

    @FXML // fx:id="lcYTravelerCount"
    private NumberAxis lcYTravelerCount; // Value injected by FXMLLoader

    @FXML // fx:id="ttvBookings"
    private TreeTableView<Booking> ttvBookings; // Value injected by FXMLLoader

    @FXML // fx:id="ttvColBooking"
    private TreeTableColumn<Booking, String> ttvColBooking; // Value injected by FXMLLoader

    @FXML // fx:id="welcomeText"
    private Label welcomeText; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcTravelerGraph != null : "fx:id=\"lcTravelerGraph\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcXDate != null : "fx:id=\"lcXDate\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcYTravelerCount != null : "fx:id=\"lcYTravelerCount\" was not injected: check your FXML file 'main-view.fxml'.";
        assert ttvBookings != null : "fx:id=\"ttvBookings\" was not injected: check your FXML file 'main-view.fxml'.";
        assert ttvColBooking != null : "fx:id=\"ttvColBooking\" was not injected: check your FXML file 'main-view.fxml'.";
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'main-view.fxml'.";


    }

}
