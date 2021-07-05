package com.consoleApp;

public class Passenger {
    private String preferredSeat;
    private String bookedSeat;
    private String bookedCoach;
    private String bookedCoachType;
    private String bookedSeatType;
    private String passengerName;
    private String bookingStatus;
    int passengerAge;

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookedCoachType() {
        return bookedCoachType;
    }

    public void setBookedCoachType(String bookedCoachType) {
        this.bookedCoachType = bookedCoachType;
    }

    public String getBookedSeatType() {
        return bookedSeatType;
    }

    public void setBookedSeatType(String bookedSeatType) {
        this.bookedSeatType = bookedSeatType;
    }

    public String getBookedCoach() {
        return bookedCoach;
    }

    public void setBookedCoach(String bookedCoach) {
        this.bookedCoach = bookedCoach;
    }

    public String getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(String bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public void setPreferredSeat(String preferredSeat) {
        this.preferredSeat = preferredSeat;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setPassengerAge(int passengerAge) {
        this.passengerAge = passengerAge;
    }
}
