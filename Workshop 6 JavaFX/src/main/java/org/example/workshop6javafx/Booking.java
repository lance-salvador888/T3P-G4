package org.example.workshop6javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Booking {
    private SimpleIntegerProperty bookingId;
    private SimpleStringProperty bookingDate;
    private SimpleStringProperty bookingNo;
    private SimpleIntegerProperty bookingTravelerCount;
    private SimpleIntegerProperty bookingCustomerId;
    private SimpleStringProperty bookingTripTypeId;
    // private SimpleIntegerProperty PackageId;
    // ^commenting this out rn cause idk if it's gonna get used

    public Booking(int bookingId, String bookingDate, String bookingNo,
                   int travelerCount, int customerId, String tripTypeId ) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.bookingTravelerCount = new SimpleIntegerProperty(travelerCount);
        this.bookingCustomerId = new SimpleIntegerProperty(customerId);
        this.bookingTripTypeId = new SimpleStringProperty(tripTypeId);
    }

    public int getBookingId() {
        return bookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public SimpleStringProperty bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public SimpleStringProperty bookingNoProperty() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public int getBookingTravelerCount() {
        return bookingTravelerCount.get();
    }

    public SimpleIntegerProperty bookingTravelerCountProperty() {
        return bookingTravelerCount;
    }

    public void setBookingTravelerCount(int bookingTravelerCount) {
        this.bookingTravelerCount.set(bookingTravelerCount);
    }

    public int getBookingCustomerId() {
        return bookingCustomerId.get();
    }

    public SimpleIntegerProperty bookingCustomerIdProperty() {
        return bookingCustomerId;
    }

    public void setBookingCustomerId(int bookingCustomerId) {
        this.bookingCustomerId.set(bookingCustomerId);
    }

    public String getBookingTripTypeId() {
        return bookingTripTypeId.get();
    }

    public SimpleStringProperty bookingTripTypeIdProperty() {
        return bookingTripTypeId;
    }

    public void setBookingTripTypeId(String bookingTripTypeId) {
        this.bookingTripTypeId.set(bookingTripTypeId);
    }

    @Override
    public String toString() {
        return "Booking " + bookingId.getValue()+
                ": bookingDate=" + bookingDate.get() +
                ", bookingNo=" + bookingNo.get() +
                ", bookingTravelerCount=" + bookingTravelerCount.get() +
                ", bookingCustomerId=" + bookingCustomerId.getValue() +
                ", bookingTripTypeId=" + bookingTripTypeId.get();
    }
}
