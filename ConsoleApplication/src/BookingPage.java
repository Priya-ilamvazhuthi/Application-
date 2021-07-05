package com.consoleApp;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        bookingInputs();
        new PaymentPage();
        System.out.println("------Ticket booked Successfully------");
        System.out.println();
        System.out.println("Your booking ID is:" + ticket.getBookingID());
        System.out.println();
        UserUI.userLogged();
    }

    void bookingInputs() {
        JSONArray trains = listTrains();
        System.out.println("Enter the train ");
        index = Console.getChoice(trains.size());
        JSONObject jsonObject = (JSONObject) trains.get((index - 1));
        String boardingTrain = jsonObject.get("Train_Name").toString();
        String boardingTrainNum = jsonObject.get("Train_Number").toString();

        setBoardingTrainIndex(index);
        setBoardingTrainNumber(boardingTrainNum);
        setBoardingTrain(boardingTrain);

        System.out.println("Select the coach ");
        try {
            Object obj = trains.get((index - 1));
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(obj);
            jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray coachArray = (JSONArray) jsonObject.get("Coaches");
            for (int i = 0; i < coachArray.size(); i++) {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(coachArray.get(i));
                System.out.println("[" + (i + 1) + "] " + json);
            }

            int coachIndex = Console.getChoice(coachArray.size());
            setBoardingCoachIndex(coachIndex);
            JSONObject coachObject = (JSONObject) coachArray.get((coachIndex - 1));
            coachName = coachObject.get("Coach_Name_" + coachIndex).toString();
            coachType = coachObject.get("Coach_Type_" + coachIndex).toString();
            fare = Integer.parseInt(coachObject.get("Coach_Fare_" + coachIndex).toString());
            getSeat(coachArray, coachIndex);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    void getSeat(JSONArray coachArray, int index1) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number of Seats :");
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

        String[] availSeat = new String[count];
        String[] availSeatType = new String[count];
        for (int i = 0; i < seatArray.size(); i++) {
            JSONObject newObject = (JSONObject) seatArray.get(i);
            if (newObject.get("Seat_Status_" + (i + 1)).toString().equals("Available")) {
                int k = 0;
                availSeat[k] = newObject.get("Seat_Number_" + (i + 1)).toString();
                availSeatType[k] = newObject.get("Seat_Type_" + (i + 1)).toString();
                k++;
            }
        }

        if (numOfTickets > count) {
            System.out.println("----Seats not available----");
        } else {
            System.out.println("Seats available");

        }
        setNumOfTickets(numOfTickets);
        createPassengers();

        for (int i = 0; i < numOfTickets; i++) {
            try {
                int k = 0;
                passengers[i].setBookedSeat(availSeat[k]);
                passengers[i].setBookedSeatType(availSeatType[k]);
                passengers[i].setBookedCoach(coachName);
                passengers[i].setBookedCoachType(coachType);
                k++;
                if (i > (availSeat.length - 1))
                    passengers[i].setBookingStatus("Waiting");
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
                    x = Integer.parseInt(input.readLine());
                    passengers[i].setPreferredSeat(UpdateTrainUI.seatTypes[x]);
                } while (!(x > 0 && x < ((UpdateTrainUI.seatTypes.length) + 1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
