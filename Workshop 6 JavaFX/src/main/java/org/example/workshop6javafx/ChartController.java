/**
 * Sample Skeleton for 'chart.fxml' Controller Class
 */

package org.example.workshop6javafx;

import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

public class ChartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="apChart"
    private AnchorPane apChart; // Value injected by FXMLLoader

    @FXML // fx:id="lcCategoryAxis"
    private CategoryAxis lcCategoryAxis; // Value injected by FXMLLoader

    @FXML // fx:id="lcLineChart"
    private LineChart<?, ?> lcLineChart; // Value injected by FXMLLoader

    @FXML // fx:id="lcNumberAxis"
    private NumberAxis lcNumberAxis; // Value injected by FXMLLoader

    @FXML // fx:id="pcPieChart"
    private PieChart pcPieChart; // Value injected by FXMLLoader

    @FXML // fx:id="sbcBarChart"
    private StackedBarChart<?, ?> sbcBarChart; // Value injected by FXMLLoader

    @FXML // fx:id="sbcCategoryAxis"
    private CategoryAxis sbcCategoryAxis; // Value injected by FXMLLoader

    @FXML // fx:id="sbcNumberAxis"
    private NumberAxis sbcNumberAxis; // Value injected by FXMLLoader

    @FXML // fx:id="tbBar"
    private Icon tbBar; // Value injected by FXMLLoader

    @FXML // fx:id="tbLine"
    private Icon tbLine; // Value injected by FXMLLoader

    @FXML // fx:id="tbPie"
    private Icon tbPie; // Value injected by FXMLLoader

    @FXML // fx:id="tbgCharts"
    private ToggleButtonGroup tbgCharts; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert apChart != null : "fx:id=\"apChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert lcCategoryAxis != null : "fx:id=\"lcCategoryAxis\" was not injected: check your FXML file 'chart.fxml'.";
        assert lcLineChart != null : "fx:id=\"lcLineChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert lcNumberAxis != null : "fx:id=\"lcNumberAxis\" was not injected: check your FXML file 'chart.fxml'.";
        assert pcPieChart != null : "fx:id=\"pcPieChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert sbcBarChart != null : "fx:id=\"sbcBarChart\" was not injected: check your FXML file 'chart.fxml'.";
        assert sbcCategoryAxis != null : "fx:id=\"sbcCategoryAxis\" was not injected: check your FXML file 'chart.fxml'.";
        assert sbcNumberAxis != null : "fx:id=\"sbcNumberAxis\" was not injected: check your FXML file 'chart.fxml'.";
        assert tbBar != null : "fx:id=\"tbBar\" was not injected: check your FXML file 'chart.fxml'.";
        assert tbLine != null : "fx:id=\"tbLine\" was not injected: check your FXML file 'chart.fxml'.";
        assert tbPie != null : "fx:id=\"tbPie\" was not injected: check your FXML file 'chart.fxml'.";
        assert tbgCharts != null : "fx:id=\"tbgCharts\" was not injected: check your FXML file 'chart.fxml'.";



    }
    public void populateLineChart() {
//        String selectedDestination = (String) cbBookings.getValue();

//        lcTravelerGraph.getData().clear();
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
//            lcTravelerGraph.getData().add(series);
            conn.close();
        }  catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}