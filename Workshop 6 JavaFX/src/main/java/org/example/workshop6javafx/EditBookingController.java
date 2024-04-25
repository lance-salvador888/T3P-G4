package org.example.workshop6javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditBookingController {

    @FXML
    private TextField bookingIdTextField;
    @FXML
    private TextField bookingNumberTextField;
    @FXML
    private TextField travelerCountTextField;
    @FXML
    private ComboBox<Integer> customerIdComboBox;
    @FXML
    private ComboBox<String> tripTypeComboBox;
    @FXML
    private ComboBox<Integer> packageIdComboBox;

    public void initialize() {
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        populateCustomerComboBox();
        populateTripTypeComboBox();
        populatePackageComboBox();
    }

    private void populateCustomerComboBox() {
        String query = "SELECT CustomerId FROM customers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customerIdComboBox.getItems().add(rs.getInt("CustomerId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTripTypeComboBox() {
        String query = "SELECT TripTypeId FROM triptypes";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tripTypeComboBox.getItems().add(rs.getString("TripTypeId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populatePackageComboBox() {
        String query = "SELECT PackageId FROM packages";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                packageIdComboBox.getItems().add(rs.getInt("PackageId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String updateQuery = "UPDATE bookings SET BookingNo=?, TravelerCount=?, CustomerId=?, TripTypeId=?, PackageId=? WHERE BookingId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, bookingNumberTextField.getText());
            pstmt.setInt(2, Integer.parseInt(travelerCountTextField.getText()));
            pstmt.setInt(3, customerIdComboBox.getValue());
            pstmt.setString(4, tripTypeComboBox.getValue());
            if (packageIdComboBox.getValue() == null) {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(5, packageIdComboBox.getValue());
            }
            pstmt.setInt(6, Integer.parseInt(bookingIdTextField.getText()));
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Update successful, " + rowsAffected + " rows affected.");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Booking edited successfully.");
            successAlert.showAndWait();
            closeWindow();
        } catch (SQLException e) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Edit Failed");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Booking edit failed.");
            successAlert.showAndWait();
            e.printStackTrace();
        }
    }

    public void setBookingData(Booking booking) {
        if (booking != null) {
            bookingIdTextField.setText(String.valueOf(booking.getBookingId()));
            bookingNumberTextField.setText(booking.getBookingNo());
            travelerCountTextField.setText(String.valueOf(booking.getTravelerCount()));
            customerIdComboBox.setValue(booking.getCustomerId());
            tripTypeComboBox.setValue(booking.getTripTypeId());
            packageIdComboBox.setValue(booking.getPackageId() != null &&
                    !booking.getPackageId().equals("N/A") ? Integer.valueOf(booking.getPackageId()) : null);
        }
    }


    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) bookingIdTextField.getScene().getWindow();
        stage.close();
    }
}
