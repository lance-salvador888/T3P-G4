/**
 * Controller for 'AddBooking.fxml'
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddBookingController {

    @FXML
    private TextField bookingIdTextField;
    @FXML
    private TextField bookingNumberTextField;
    @FXML
    private TextField travelerCountTextField;
    @FXML
    private ComboBox<String> customerIdComboBox;
    @FXML
    private ComboBox<String> tripTypeComboBox;
    @FXML
    private ComboBox<String> packageIdComboBox;
    @FXML
    private Label dateLabel;

    @FXML
    private void initialize() {
        initializeComboBoxes();
        dateLabel.setText(LocalDate.now().toString());
    }

    private void initializeComboBoxes() {
        populateCustomerComboBox();
        populateTripTypeComboBox();
        populatePackageComboBox();
    }

    private void populateCustomerComboBox() {
        String query = "SELECT CustomerId FROM customers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ArrayList<String> customerIds = new ArrayList<>();
            while (rs.next()) {
                customerIds.add(rs.getString("CustomerId"));
            }
            customerIdComboBox.getItems().addAll(customerIds);
        } catch (Exception e) {
            System.err.println("Error populating customer combo box: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateTripTypeComboBox() {
        String query = "SELECT TripTypeId FROM triptypes";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ArrayList<String> tripTypes = new ArrayList<>();
            while (rs.next()) {
                tripTypes.add(rs.getString("TripTypeId"));
            }
            tripTypeComboBox.getItems().addAll(tripTypes);
        } catch (Exception e) {
            System.err.println("Error populating trip type combo box: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populatePackageComboBox() {
        String query = "SELECT PackageId FROM packages";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ArrayList<String> packageIds = new ArrayList<>();
            while (rs.next()) {
                packageIds.add(rs.getString("PackageId"));
            }
            packageIdComboBox.getItems().addAll(packageIds);
        } catch (Exception e) {
            System.err.println("Error populating package combo box: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String packageId = packageIdComboBox.getValue();
            Integer packageIdInt = (packageId != null && !packageId.equals("N/A")) ? Integer.valueOf(packageId) : null;

            String insertQuery = "INSERT INTO bookings (BookingId, BookingDate, BookingNo, TravelerCount, " +
                    "CustomerId, TripTypeId, PackageId) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, Integer.parseInt(bookingIdTextField.getText()));
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Set BookingDate to current date
                pstmt.setString(3, bookingNumberTextField.getText());
                pstmt.setInt(4, Integer.parseInt(travelerCountTextField.getText()));
                pstmt.setString(5, customerIdComboBox.getValue());
                pstmt.setString(6, tripTypeComboBox.getValue());
                pstmt.setString(7, packageIdComboBox.getValue());
                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Successful");
                alert.setHeaderText(null);
                alert.setContentText("Booking saved successfully.");
                alert.showAndWait();

                closeWindow(event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to save booking. Please try again.");
            alert.showAndWait();

            System.err.println("Error saving booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
