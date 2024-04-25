/**
 * Class for the 'Booking' table from the 'travelexperts' database
 * By: Navreet Dhillon
 */

package org.example.workshop6javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Booking {
    private final SimpleIntegerProperty bookingId;
    private final SimpleObjectProperty<LocalDate> bookingDate;
    private final SimpleStringProperty bookingNo;
    private final SimpleIntegerProperty travelerCount;
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty tripTypeId;
    private final SimpleObjectProperty<Integer> packageId;

    public Booking(int bookingId, LocalDate bookingDate, String bookingNo, int travelerCount,
                   int customerId, String tripTypeId, Integer packageId) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.bookingDate = new SimpleObjectProperty<>(bookingDate);
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travelerCount = new SimpleIntegerProperty(travelerCount);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.packageId = new SimpleObjectProperty<>(packageId);
    }


    public int getBookingId() {
        return bookingId.get();
    }
    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }
    public SimpleIntegerProperty bookingIdProperty() {
        return bookingId;
    }


    public String getBookingDate() {
        LocalDate date = bookingDate.get();
        return date != null ? date.toString() : "N/A";
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate.set(bookingDate);
    }
    public SimpleObjectProperty<LocalDate> bookingDateProperty() {
        return bookingDate;
    }


    public String getBookingNo() {
        return bookingNo.get();
    }
    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }
    public SimpleStringProperty bookingNoProperty() {
        return bookingNo;
    }


    public int getTravelerCount() {
        return travelerCount.get();
    }
    public void setTravelerCount(int travelerCount) {
        this.travelerCount.set(travelerCount);
    }
    public SimpleIntegerProperty travelerCountProperty() {
        return travelerCount;
    }


    public int getCustomerId() {
        return customerId.get();
    }
    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }


    public String getTripTypeId() {
        return tripTypeId.get();
    }
    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }
    public SimpleStringProperty tripTypeIdProperty() {
        return tripTypeId;
    }


    public String getPackageId() {
        Integer id = packageId.get();
        return id != null ? String.valueOf(id) : "N/A";
    }
    public void setPackageId(String packageIdStr) {
        if (packageIdStr != null) {
            this.packageId.set(Integer.parseInt(packageIdStr));
        } else {
            this.packageId.set(null);
        }
    }
}
