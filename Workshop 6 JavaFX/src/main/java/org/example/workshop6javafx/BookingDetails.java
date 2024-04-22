package org.example.workshop6javafx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class BookingDetails {
    SimpleIntegerProperty bookingDetailId;
    SimpleDoubleProperty itineraryNo;
    SimpleStringProperty tripStart;
    SimpleStringProperty tripEnd;
    SimpleStringProperty description;
    SimpleStringProperty destination;
    SimpleDoubleProperty basePrice;
    SimpleDoubleProperty agencyCommission;

    public BookingDetails(int bookingDetailId, double itineraryNo,
                          String tripStart, String tripEnd,
                          String description, String destination,
                          double basePrice, double agencyCommission) {
        this.bookingDetailId = new SimpleIntegerProperty(bookingDetailId);
        this.itineraryNo = new SimpleDoubleProperty(itineraryNo);
        this.tripStart = new SimpleStringProperty(tripStart);
        this.tripEnd = new SimpleStringProperty(tripEnd);
        this.description = new SimpleStringProperty(description);
        this.destination = new SimpleStringProperty(destination);
        this.basePrice = new SimpleDoubleProperty(basePrice);
        this.agencyCommission = new SimpleDoubleProperty(agencyCommission);
    }

    public int getBookingDetailId() {
        return bookingDetailId.get();
    }

    public SimpleIntegerProperty bookingDetailIdProperty() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId.set(bookingDetailId);
    }

    public double getItineraryNo() {
        return itineraryNo.get();
    }

    public SimpleDoubleProperty itineraryNoProperty() {
        return itineraryNo;
    }

    public void setItineraryNo(double itineraryNo) {
        this.itineraryNo.set(itineraryNo);
    }

    public String getTripStart() {
        return tripStart.get();
    }

    public SimpleStringProperty tripStartProperty() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart.set(tripStart);
    }

    public String getTripEnd() {
        return tripEnd.get();
    }

    public SimpleStringProperty tripEndProperty() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd.set(tripEnd);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public double getBasePrice() {
        return basePrice.get();
    }

    public SimpleDoubleProperty basePriceProperty() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice.set(basePrice);
    }

    public double getAgencyCommission() {
        return agencyCommission.get();
    }

    public SimpleDoubleProperty agencyCommissionProperty() {
        return agencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.agencyCommission.set(agencyCommission);
    }
}
