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
import java.time.LocalDate;

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

        populateBarGraph();
    }
    public void populateLineChart() {
        lcLineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("Travelers per Year");

        String url = "";
        String user = "";
        String password = "";
        String sql = "SELECT YEAR(bookingDate) AS bookingYear, SUM(TravelerCount) AS TotalTravelers "
                + "FROM bookings "
                + "GROUP BY YEAR(bookingDate)";  // Group by year to get yearly traveler count sum

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

            // Use a HashMap to store year and total traveler count (optional)
            // HashMap<Integer, Integer> travelerCountPerYear = new HashMap<>();

            while (rs.next()) {
                int year = rs.getInt(1);
                int totalCount = rs.getInt(2);
                series.getData().add(new XYChart.Data(String.valueOf(year), totalCount));  // Convert year to String
            }


            lcLineChart.getData().add(series);

            // Set Y-axis label (optional)
            lcLineChart.getYAxis().setLabel("Total Travelers");

            rs.close();
            stmt.close();
            conn.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print the exception trace for debugging
            throw new RuntimeException(e);
        }
    }
    public void populateBarGraph() {
        sbcBarChart.getData().clear();

        XYChart.Series series = new XYChart.Series();
        series.setName("Travelers per Day of Week");

        String url = "";
        String user = "";
        String password = "";

        String sql = "SELECT DAYOFWEEK(bookingDate) AS bookingDay, SUM(TravelerCount) AS totalTravelers "
                + "FROM bookings "
                + "GROUP BY DAYOFWEEK(bookingDate)";

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

            while (rs.next()) {
                int bookingDay = rs.getInt(1);
                int totalTravelers = rs.getInt(2);


                String dayOfWeek = LocalDate.of(2024, 1, bookingDay).getDayOfWeek().toString();


                series.getData().add(new XYChart.Data<>(dayOfWeek, totalTravelers));
            }

            sbcBarChart.getData().add(series);
            sbcBarChart.getXAxis().setLabel("Day of Week");


            rs.close();
            stmt.close();
            conn.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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