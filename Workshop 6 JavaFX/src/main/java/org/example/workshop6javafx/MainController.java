/**
 * Sample Skeleton for 'main-view.fxml' Controller Class
 */

package org.example.workshop6javafx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML // fx:id="tvBookings"
    private TreeView<String> tvBookings; // Value injected by FXMLLoader

    @FXML // fx:id="welcomeText"
    private Label welcomeText; // Value injected by FXMLLoader


    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private ObservableList<BookingDetails> bookingDetails = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcTravelerGraph != null : "fx:id=\"lcTravelerGraph\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcXDate != null : "fx:id=\"lcXDate\" was not injected: check your FXML file 'main-view.fxml'.";
        assert lcYTravelerCount != null : "fx:id=\"lcYTravelerCount\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvBookings != null : "fx:id=\"tvBookings\" was not injected: check your FXML file 'main-view.fxml'.";
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'main-view.fxml'.";

        getBookings();
        getBookingDetails();

        showBookings();
    }


    private void getBookings(){
        bookings.clear();

        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            while(rs.next()){
                bookings.add(new Booking(
                        rs.getInt(1), rs.getDate(2).toString(),
                        rs.getString(3), rs.getInt(4),
                        rs.getInt(5), rs.getString(6)));
            }
            conn.close();
        }  catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBookingDetails(){
        bookingDetails.clear();

        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookingdetails");
            while(rs.next()){
                bookingDetails.add(new BookingDetails(
                        rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6),
                        rs.getDouble(7), rs.getDouble(8),
                        rs.getInt(9), rs.getString(10),
                        rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }  catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showBookings() {
        TreeItem<String> root = new TreeItem<>("Bookings");
        for (Booking booking : bookings){
            TreeItem<String> formattedBooking = new TreeItem<>(booking.toString());

            for (BookingDetails bookingDetails : bookingDetails) {
                if (booking.getBookingId()
                        == bookingDetails.getBookingId()){
                    formattedBooking.getChildren()
                                    .add(new TreeItem<>(bookingDetails.toString()));
                }
            }

            root.getChildren().add(formattedBooking);
        }

        tvBookings.setRoot(root);
    }
}
