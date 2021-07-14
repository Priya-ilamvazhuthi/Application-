package com.consoleApp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookingPage extends BookingSystem {
    static int index;
    static String coachName, coachType;
    static int fare;

    BookingPage(){
        System.out.println("---------------Booking----------------");
        System.out.println();
        new EnquirePage();
        setBoardingStation(EnquirePage.source);
        setDestination(EnquirePage.destination);
        setTravelDay(EnquirePage.travelDate);
        bookingInputs();
        int fare = getTotalFare();
        if(PaymentPage.getConfirmation(fare)) {
            System.out.println("------Ticket booked Successfully------");
            bookTicket();
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("|     Your booking ID is: "+ ticket.getBookingID() +"       |");
            System.out.println("--------------------------------------");
            System.out.println();
        }
        else {
            System.out.println();
            System.out.println("------Ticket booked Unsuccessful------");
        }
        System.out.println();
        UserUI.userLogged();
    }

    void bookingInputs() {
        System.out.println("Enter the train ");
        System.out.println();
        index = Console.getChoice(EnquirePage.availableTrains.size());
        JSONObject jsonObject = (JSONObject) EnquirePage.availableTrains.get((index - 1));
        String boardingTrain = jsonObject.get("Train_Name").toString();
        String boardingTrainNum = jsonObject.get("Train_Number").toString();

        setBoardingTrainIndex(index);
        setBoardingTrainNumber(boardingTrainNum);
        setBoardingTrain(boardingTrain);

        System.out.println("Select the coach ");
        System.out.println();

        try {
            Object obj = EnquirePage.availableTrains.get((index - 1));
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(obj);
            jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray coachArray = (JSONArray) jsonObject.get("Coaches");

            for (int i = 0; i < coachArray.size(); i++) {
                JSONObject coachObject = (JSONObject) coachArray.get(i);
                System.out.println("["+(i+1)+"] Coach Name                :  "+coachObject.get("Coach_Name_"+(i+1)));
                System.out.println("    Coach Type                :  "+coachObject.get("Coach_Type_"+(i+1)));
                System.out.println("    Fare                      :  "+coachObject.get("Coach_Fare_"+(i+1)));
                System.out.println("    Available number of seats :  "+EnquirePage.seatsOnCoach[getBoardingTrainIndex()-1][i]);
                System.out.println("    Total number of Seats     :  "+EnquirePage.totalSeats[getBoardingTrainIndex()-1][i]);
                System.out.println();
            }

            int coachIndex = Console.getChoice(coachArray.size());
            setBoardingCoachIndex(coachIndex);
            JSONObject coachObject = (JSONObject) coachArray.get((coachIndex - 1));
            coachName = coachObject.get("Coach_Name_" + coachIndex).toString();
            coachType = coachObject.get("Coach_Type_" + coachIndex).toString();
            fare = Integer.parseInt(coachObject.get("Coach_Fare_" + coachIndex).toString());
            getSeat(coachArray, coachIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    void getSeat(JSONArray coachArray, int index1) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the number of Seats : ");
        System.out.println();
        int numOfTickets = 0;
        try {
            numOfTickets = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject coachObject = (JSONObject) coachArray.get((index1 - 1));
        JSONArray seatArray = (JSONArray) coachObject.get("Seats_" + index1);
        int count = 0;

        for (int i = 0; i < seatArray.size(); i++) {
            JSONObject newObject = (JSONObject) seatArray.get(i);
            if (newObject.get("Seat_Status_" + (i + 1)).toString().equals("Available")) {
                count++;
            }
        }

        System.out.println();
        if(numOfTickets > EnquirePage.totalSeats[getBoardingTrainIndex()-1][getBoardingCoachIndex()-1]) {
            System.out.println("Number of seats exceeds seats on coach");
            System.out.println();
            new BookingPage();
        }
        if (numOfTickets > count) {
            System.out.println("--Some seats will be in waiting list--");
        } else {
            System.out.println("----------All seats Confirmed---------");

        }
        System.out.println();

        setNumOfTickets(numOfTickets);
        createPassengers();

        int m=0;
        for (int i = 0; i < numOfTickets; i++) {
            try {
                passengers[i].setBookedSeat(EnquirePage.availSeat[getBoardingTrainIndex()-1][getBoardingCoachIndex()-1][m]);
                passengers[i].setBookedSeatType(EnquirePage.availSeatType[getBoardingTrainIndex()-1][getBoardingCoachIndex()-1][m]);
                m++;
                passengers[i].setBookedCoach(coachName);
                passengers[i].setBookedCoachType(coachType);
                if(i >= EnquirePage.seatsOnCoach[getBoardingTrainIndex()-1][getBoardingCoachIndex()-1]) {

                    passengers[i].setBookingStatus("Waiting");
                }
                else
                    passengers[i].setBookingStatus("Confirmed");
                ticket.setFare(fare);
                System.out.println("Enter Passenger " + (i + 1) + " Name :");
                passengers[i].setPassengerName(input.readLine());
                System.out.println("Enter Passenger " + (i + 1) + " Age :");
                passengers[i].setPassengerAge(Integer.parseInt(input.readLine()));
                String[] seatType =UpdateTrainUI.seatTypes;
                for (int j = 0; j < seatType.length; j++) {
                    System.out.println("[" + (j + 1) + "] " + seatType[j]);
                }
                int x;
                do {
                    System.out.println("Select Passenger " + (i + 1) + " Preferred birth from above list:");
                    System.out.println();
                    x = Integer.parseInt(input.readLine());
                    passengers[i].setPreferredSeat(UpdateTrainUI.seatTypes[x]);
                } while (!(x > 0 && x < ((UpdateTrainUI.seatTypes.length) + 1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
