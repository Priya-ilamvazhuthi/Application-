package com.consoleApp;

public class Seat {
    public int seatNumber;
    public String seatType;
    public String seatStatus;
    String PassengerName;
    int bookingID;

    public String getPassengerName() {
        return PassengerName;
    }
    public void setPassengerName(String passengerName) {
        PassengerName = passengerName;
    }
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String getSeatType() {
        return seatType;
    }
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    public String getSeatStatus() {
        return seatStatus;
    }
    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }
}
