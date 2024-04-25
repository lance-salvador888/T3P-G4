/**
 * Controller Class for 'AddBookingDetails.fxml'
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class AddBookingDetailsController {

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
    private Button btnSave;
    @FXML
    private Button btnClose;

    private int bookingId;

    @FXML
    public void initialize() {
        loadComboBoxes();
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            // Get data from input fields
            LocalDate tripStartDate = tripStartDatePicker.getValue();
            LocalDate tripEndDate = tripEndDatePicker.getValue();
            double itineraryNo = Double.parseDouble(itineraryNoTextField.getText());
            String description = descriptionTextField.getText();
            String destination = destinationTextField.getText();
            double basePrice = Double.parseDouble(basePriceTextField.getText());
            double agencyCommission = Double.parseDouble(agencyCommissionTextField.getText());
            String regionId = regionidcbox.getValue();
            String classId = classidcbox.getValue();
            String feeId = feeidcbox.getValue();
            int productSupplierId = productSupplierIdComboBox.getValue();

            // Perform database operation to save data
            String insertQuery = "INSERT INTO bookingdetails (BookingId, TripStart, TripEnd, " +
                    "ItineraryNo, Description, Destination, BasePrice, AgencyCommission, RegionId, " +
                    "ClassId, FeeId, ProductSupplierId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

                pstmt.setInt(1, bookingId);
                pstmt.setDate(2, Date.valueOf(tripStartDate));
                pstmt.setDate(3, Date.valueOf(tripEndDate));
                pstmt.setDouble(4, itineraryNo);
                pstmt.setString(5, description);
                pstmt.setString(6, destination);
                pstmt.setDouble(7, basePrice);
                pstmt.setDouble(8, agencyCommission);
                pstmt.setString(9, regionId);
                pstmt.setString(10, classId);
                pstmt.setString(11, feeId);
                pstmt.setInt(12, productSupplierId);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking details saved successfully.");
                    successAlert.showAndWait();
                    closeWindow(event);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to save booking details. Please try again.");
                    errorAlert.showAndWait();

                }
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to save booking details. Please try again.");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please enter valid numerical values.");
            errorAlert.showAndWait();
            e.printStackTrace();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to save booking details. Please try again.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        closeWindow(event);
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
