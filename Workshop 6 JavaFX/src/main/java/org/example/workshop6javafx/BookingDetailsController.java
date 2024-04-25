/**
 * Controller Class for 'BookingDetails.fxml'
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class BookingDetailsController {

    @FXML
    private ComboBox<String> feeidcbox;
    @FXML
    private ComboBox<String> regionidcbox;
    @FXML
    private ComboBox<String> classidcbox;
    @FXML
    private DatePicker tripStartDatePicker;
    @FXML
    private DatePicker tripEndDatePicker;
    @FXML
    private ComboBox<Integer> productSupplierIdComboBox;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField itineraryNoTextField;
    @FXML
    private TextField destinationTextField;
    @FXML
    private TextField agencyCommissionTextField;
    @FXML
    private TextField basePriceTextField;

    @FXML
    private Button btnEdit;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnDelete;

    private int bookingId;


    @FXML
    public void initialize() {
        setFieldsEditable(false);
        loadComboBoxes();
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        System.out.println("Save button clicked!");
        try {

            LocalDate tripStartDate = tripStartDatePicker.getValue();
            LocalDate tripEndDate = tripEndDatePicker.getValue();
            double itineraryNo = Double.parseDouble(itineraryNoTextField.getText());
            String description = descriptionTextField.getText();
            String destination = destinationTextField.getText();
            BigDecimal basePrice = new BigDecimal(basePriceTextField.getText());
            BigDecimal agencyCommission = new BigDecimal(agencyCommissionTextField.getText());
            String regionId = regionidcbox.getValue();
            String classId = classidcbox.getValue();
            String feeId = feeidcbox.getValue();
            int productSupplierId = productSupplierIdComboBox.getValue();


            String updateQuery = "UPDATE bookingdetails SET TripStart=?, TripEnd=?, ItineraryNo=?, Description=?, " +
                    "Destination=?, BasePrice=?, AgencyCommission=?, RegionId=?, ClassId=?, FeeId=?, ProductSupplierId=? " +
                    "WHERE BookingId=?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setDate(1, Date.valueOf(tripStartDate));
                pstmt.setDate(2, Date.valueOf(tripEndDate));
                pstmt.setDouble(3, itineraryNo);
                pstmt.setString(4, description);
                pstmt.setString(5, destination);
                pstmt.setBigDecimal(6, basePrice);
                pstmt.setBigDecimal(7, agencyCommission);
                pstmt.setString(8, regionId);
                pstmt.setString(9, classId);
                pstmt.setString(10, feeId);
                pstmt.setInt(11, productSupplierId);
                pstmt.setInt(12, bookingId);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) updated");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Successful");
            alert.setHeaderText(null);
            alert.setContentText("Booking details saved successfully.");
            alert.showAndWait();
            closeWindow(event);
        } catch (Exception e) {
            // Display an error pop-up
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to save booking details. Please try again.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void onEditButtonClick(ActionEvent event) {
        setFieldsEditable(true);
        btnEdit.setDisable(true);
    }

    private void setFieldsEditable(boolean value) {
        tripStartDatePicker.setEditable(value);
        tripEndDatePicker.setEditable(value);
        descriptionTextField.setEditable(value);
        itineraryNoTextField.setEditable(value);
        destinationTextField.setEditable(value);
        agencyCommissionTextField.setEditable(value);
        basePriceTextField.setEditable(value);
        feeidcbox.setDisable(!value);
        productSupplierIdComboBox.setDisable(!value);
        regionidcbox.setDisable(!value);
        classidcbox.setDisable(!value);
        productSupplierIdComboBox.setDisable(!value);
    }

    public void loadBookingDetails(int bookingId) {
        this.bookingId = bookingId;
        String query = "SELECT * FROM bookingdetails WHERE BookingId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tripStartDatePicker.setValue(rs.getDate("TripStart").toLocalDate());
                tripEndDatePicker.setValue(rs.getDate("TripEnd").toLocalDate());
                itineraryNoTextField.setText(Double.toString(rs.getDouble("ItineraryNo")));
                descriptionTextField.setText(rs.getString("Description"));
                destinationTextField.setText(rs.getString("Destination"));
                basePriceTextField.setText(rs.getString("BasePrice"));
                agencyCommissionTextField.setText(rs.getBigDecimal("AgencyCommission").toPlainString());

                feeidcbox.setValue(rs.getString("FeeId"));
                productSupplierIdComboBox.setValue(Integer.valueOf(rs.getString("ProductSupplierId")));
                regionidcbox.setValue(rs.getString("RegionId"));
                classidcbox.setValue(rs.getString("ClassId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComboBoxes() {
        try (Connection conn = DBConnection.getConnection()) {

            String feeIdQuery = "SELECT FeeId FROM fees";
            PreparedStatement feeIdStmt = conn.prepareStatement(feeIdQuery);
            ResultSet feeIdRs = feeIdStmt.executeQuery();
            ObservableList<String> feeIdList = FXCollections.observableArrayList();
            while (feeIdRs.next()) {
                feeIdList.add(feeIdRs.getString("FeeId"));
            }
            feeidcbox.setItems(feeIdList);


            String productSupplierIdQuery = "SELECT ProductSupplierId FROM products_suppliers";
            PreparedStatement productSupplierIdStmt = conn.prepareStatement(productSupplierIdQuery);
            ResultSet productSupplierIdRs = productSupplierIdStmt.executeQuery();
            ObservableList<Integer> productSupplierIdList = FXCollections.observableArrayList();
            while (productSupplierIdRs.next()) {
                productSupplierIdList.add(productSupplierIdRs.getInt("ProductSupplierId"));
            }
            productSupplierIdComboBox.setItems(productSupplierIdList);


            String regionIdQuery = "SELECT RegionId FROM regions";
            PreparedStatement regionIdStmt = conn.prepareStatement(regionIdQuery);
            ResultSet regionIdRs = regionIdStmt.executeQuery();
            ObservableList<String> regionIdList = FXCollections.observableArrayList();
            while (regionIdRs.next()) {
                regionIdList.add(regionIdRs.getString("RegionId"));
            }
            regionidcbox.setItems(regionIdList);

            String classIdQuery = "SELECT ClassId FROM classes";
            PreparedStatement classIdStmt = conn.prepareStatement(classIdQuery);
            ResultSet classIdRs = classIdStmt.executeQuery();
            ObservableList<String> classIdList = FXCollections.observableArrayList();
            while (classIdRs.next()) {
                classIdList.add(classIdRs.getString("ClassId"));
            }
            classidcbox.setItems(classIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        if (bookingId == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Details Selected");
            alert.setContentText("Please load booking details to delete.");
            alert.showAndWait();
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Confirm Booking Details Deletion");
        confirmAlert.setContentText("Are you sure you want to delete these booking details?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookingdetails WHERE BookingId = ?")) {
                stmt.setInt(1, bookingId);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Booking details deleted successfully.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking Details deleted successfully.");
                    successAlert.showAndWait();
                    closeWindow(event);
                } else {
                    System.out.println("No booking details found to delete.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Failed to delete booking details.");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking details could not be deleted.");
                    successAlert.showAndWait();
                }
            } catch (SQLException e) {
                System.err.println("Error executing delete query on bookingdetails table: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
        private void closeWindow (ActionEvent event){
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
}
