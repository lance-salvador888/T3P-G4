package org.example.workshop6javafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class ChartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lcLineChart"
    private LineChart<?, ?> lcLineChart; // Value injected by FXMLLoader

    @FXML // fx:id="pcPieChart"
    private PieChart pcPieChart; // Value injected by FXMLLoader

    @FXML // fx:id="sbcBarChart"
    private StackedBarChart<?, ?> sbcBarChart; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lcLineChart != null : "fx:id=\"lcLineChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert pcPieChart != null : "fx:id=\"pcPieChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert sbcBarChart != null : "fx:id=\"sbcBarChart\" was not injected: check your FXML file 'chart.fxml'.";

        populatePieChart();
    }
    public void populateLineChart() {
        //String selectedDestination = (String) cbBookings.getValue();

        lcLineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        String url = "";
        String user = "";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings order by bookingDate ");
            while(rs.next()){
                series.getData().add(new XYChart.Data(rs.getDate(2).toString(), rs.getInt(4)));
            }
            lcLineChart.getData().add(series);
            conn.close();
        }  catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void populateBarGraph() {
        sbcBarChart.getData().clear(); // Clear previous data

        XYChart.Series series = new XYChart.Series<>();



        String url = "";
        String user = "";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select TravelerCount, PackageName   from bookings order by bookingDate join packages " +
                    "on bookings.packageId = packages.packageId");


            while (rs.next()) {
                // Debug print to see if data is being retrieved correctly
                System.out.println("Date: " + rs.getDate(2) + ", Booking: " + rs.getInt(4));
                series.getData().add(new XYChart.Data<>(rs.getDate(2).toString(), rs.getInt(4)));
            }

            sbcBarChart.getData().add(series);
            conn.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print the exception trace for debugging
            throw new RuntimeException(e);
        }
    }

    public void populatePieChart() {
        pcPieChart.getData().clear();

        String url = "";
        String user = "";
        String password = "";
        String sql = "SELECT tt.TTName, SUM(b.TravelerCount) AS TotalTravelers "
                + "FROM bookings b "
                + "INNER JOIN TripTypes tt ON b.TripTypeId = tt.TripTypeId "
                + "GROUP BY tt.TTName";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            Map<String, Integer> travelerCountPerTripType = new HashMap<>();
            int totalCount = 0; // Keep track of the total traveler count
            while (rs.next()) {
                travelerCountPerTripType.put(rs.getString(1), rs.getInt(2));
                totalCount += rs.getInt(2);
            }


            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : travelerCountPerTripType.entrySet()) {
                double percentage = ((double) entry.getValue() / totalCount) * 100;
                pieChartData.add(new PieChart.Data(entry.getKey() + " (" + String.format("%.2f", percentage) + "%)", entry.getValue()));
            }


            pcPieChart.setData(pieChartData);
            pcPieChart.setLabelsVisible(false); // Hide default labels inside slices (optional)

            rs.close();
            stmt.close();
            conn.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print the exception trace for debugging
            throw new RuntimeException(e);
        }
    }


}