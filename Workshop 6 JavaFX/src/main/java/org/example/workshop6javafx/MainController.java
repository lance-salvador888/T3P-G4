/**
 * Controller Class for 'MainView.fxml'
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MainController {

    @FXML
    private TableView<Booking> tableBookings;

    @FXML
    private TableColumn<Booking, Integer> colBookingId;
    @FXML
    private TableColumn<Booking, LocalDateTime> colBookingDate;
    @FXML
    private TableColumn<Booking, String> colBookingNo;
    @FXML
    private TableColumn<Booking, Integer> colTravelerCount;
    @FXML
    private TableColumn<Booking, Integer> colCustomerId;
    @FXML
    private TableColumn<Booking, String> colTripTypeId;
    @FXML
    private TableColumn<Booking, String> colPackageId;

    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnViewDetails;
    @FXML
    private Button btnStatistics;


    @FXML
    public void initialize() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        colBookingNo.setCellValueFactory(new PropertyValueFactory<>("bookingNo"));
        colTravelerCount.setCellValueFactory(new PropertyValueFactory<>("travelerCount"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTripTypeId.setCellValueFactory(new PropertyValueFactory<>("tripTypeId"));

        colPackageId.setCellValueFactory(cellData -> {
            String packageId = cellData.getValue().getPackageId();
            return new SimpleStringProperty(packageId != null && !packageId.equals("0") ? packageId : "N/A");
        });

        refreshTable();
    }

    public void refreshTable() {
        System.out.println("Refreshing table...");
        ObservableList<Booking> bookingList = FXCollections.observableArrayList();
        String query = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking(rs.getInt("BookingId"), rs.getDate("BookingDate").toLocalDate(),
                        rs.getString("BookingNo"), rs.getInt("TravelerCount"),
                        rs.getInt("CustomerId"), rs.getString("TripTypeId"),
                        rs.getInt("PackageId"));
                bookingList.add(booking);
            }
            tableBookings.getItems().clear();
            tableBookings.getItems().addAll(bookingList);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void editBooking() {
        Booking selectedBooking = tableBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking to edit.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader editLoader = new FXMLLoader(getClass().getResource("EditBooking.fxml"));
            Parent editRoot = editLoader.load();
            EditBookingController editController = editLoader.getController();
            editController.setBookingData(selectedBooking);
            Stage editStage = new Stage();
            editStage.setTitle("Edit Booking");
            editStage.setScene(new Scene(editRoot));
            editStage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while trying to edit the booking: " + e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
        Booking selectedBooking = tableBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking to delete.");
            alert.showAndWait();
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Confirm Booking Deletion");
        confirmAlert.setContentText("Are you sure you want to delete this booking? This will delete all dependent Booking Details.");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookingdetails WHERE BookingId = ?")) {
                stmt.setInt(1, selectedBooking.getBookingId());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Booking details deleted successfully.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking Details deleted successfully.");
                    successAlert.showAndWait();
                } else {
                    System.out.println("No booking details found to delete.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("No Booking Details Found.");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking Details deletion failed.");
                    successAlert.showAndWait();
                }
            } catch (SQLException e) {
                System.err.println("Error executing delete query on bookingdetails table: " + e.getMessage());
                e.printStackTrace();
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookings WHERE BookingId = ?")) {
                stmt.setInt(1, selectedBooking.getBookingId());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Booking deleted successfully.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking deleted successfully.");
                    successAlert.showAndWait();
                    refreshTable();
                } else {
                    System.out.println("Failed to delete booking.");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Failed to delete booking.");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking deletion failed.");
                    successAlert.showAndWait();
                }
            } catch (SQLException e) {
                System.err.println("Error executing delete query on bookings table: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void viewDetails() {
        Booking selectedBooking = tableBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking to view details.");
            alert.showAndWait();
            return;
        }

        // Check if there are any booking details associated with the selected booking ID
        boolean hasBookingDetails = checkBookingDetails(selectedBooking.getBookingId());

        if (hasBookingDetails) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingDetails.fxml"));
                Parent root = loader.load();
                BookingDetailsController detailsController = loader.getController();
                detailsController.loadBookingDetails(selectedBooking.getBookingId());
                Stage stage = new Stage();
                stage.setTitle("Booking Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.err.println("An error occurred while trying to view booking details: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error occurred while trying to view booking details.");
                alert.showAndWait();
                e.printStackTrace();
            }
        } else {
            Alert noDetailsAlert = new Alert(Alert.AlertType.CONFIRMATION);
            noDetailsAlert.setTitle("No Booking Details");
            noDetailsAlert.setHeaderText("No Booking Details Found");
            noDetailsAlert.setContentText("No booking details found for this booking. Would you like to create new booking details?");
            ButtonType createDetailsButton = new ButtonType("Create Details", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            noDetailsAlert.getButtonTypes().setAll(createDetailsButton, cancelButton);
            Optional<ButtonType> result = noDetailsAlert.showAndWait();

            if (result.isPresent() && result.get() == createDetailsButton) {
                openAddBookingDetailsPage(selectedBooking);
            }
        }
    }

    private boolean checkBookingDetails(int bookingId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookingdetails WHERE BookingId = ?")) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if there are booking details, false otherwise
            }
        } catch (SQLException e) {
            System.err.println("Error checking booking details: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void openAddBookingDetailsPage(Booking selectedBooking) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBookingDetails.fxml"));
            Parent root = loader.load();
            AddBookingDetailsController controller = loader.getController();
            controller.setBookingId(selectedBooking.getBookingId());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Booking Details");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to open Add Booking Details page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // method for displaying 'chart.fxml'
    // By: Lance
    @FXML
    private void viewCharts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Bookings Infographics");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openAddBookingPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBooking.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Travel Experts Management");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to open Add Booking page: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
