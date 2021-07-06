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
        EnquirePage enquiry = new EnquirePage();
        enquiry.enquiryInputs();
        bookingInputs();
        int fare = getTotalFare();
        if(PaymentPage.getConfirmation(fare)) {
            System.out.println("------Ticket booked Successfully------");
            bookTicket();
            System.out.println("Your booking ID is:" + ticket.getBookingID());
        }
        else {
            System.out.println("------Ticket booked Unsuccessful------");
        }
        System.out.println();
        UserUI.userLogged();
    }

    void bookingInputs() {
        System.out.println();
        System.out.println("Enter the train ");
        index = Console.getChoice(EnquirePage.availableTrains.size());
        JSONObject jsonObject = (JSONObject) EnquirePage.availableTrains.get((index - 1));
        String boardingTrain = jsonObject.get("Train_Name").toString();
        String boardingTrainNum = jsonObject.get("Train_Number").toString();

        setBoardingTrainIndex(index);
        setBoardingTrainNumber(boardingTrainNum);
        setBoardingTrain(boardingTrain);

        System.out.println();
        System.out.println("Select the coach ");
        try {
            Object obj = EnquirePage.availableTrains.get((index - 1));
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
        System.out.println();
        System.out.print("Enter the number of Seats : ");
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

        System.out.println();
        if (numOfTickets > count) {
            System.out.println("--Some seats will be in waiting list--");
        } else {
            System.out.println("----------All seats Confirmed---------");

        }
        System.out.println();

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
