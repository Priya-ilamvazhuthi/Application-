package com.consoleApp;

public class Coach {
    private String coachName;
    private String coachType;
    private int fare;
    private int numOfSeats;
    Seat[] seats;

    void createSeat() {
        seats = new Seat[numOfSeats];
        for (int i = 0; i < numOfSeats; i++) {
            seats[i] = new Seat();
        }
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }
}
