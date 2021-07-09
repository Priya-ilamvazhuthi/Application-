package com.consoleApp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnquirePage extends BookingSystem {
    static JSONArray availableTrains;
    static String source, destination, travelDate;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    EnquirePage() {
        enquiryInputs();
        listAvailableTrains();
        System.out.println();
    }

    void enquiryInputs() {
        int index;
        String[] stations = listStationName();
        System.out.println("-----------Boarding Station-----------");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("[1] Search by Station Name");
        System.out.println("[2] Choose from list of stations");
        int choice = Console.getChoice(2);
        System.out.println();

        if (choice == 1) {
            System.out.print("Enter boarding station Name : ");
            try {
                source = input.readLine();
                setBoardingStation(source);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (choice == 2) {
            System.out.println("Choose the boarding station");
            printList(stations);
            index = Console.getChoice(stations.length);
            setBoardingStation(stations[index - 1]);
            source = stations[index - 1];
            System.out.println();
        }

        System.out.println("---------Destination station----------");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("[1] Search by Station Name");
        System.out.println("[2] Choose from list of stations");
        System.out.println();
        choice = Console.getChoice(2);

        if (choice == 1) {
            System.out.print("Enter destination station Name : ");
            try {
                destination = input.readLine();
                setDestination(destination);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (choice == 2) {
            System.out.println("Choose the destination station");
            printList(stations);
            index = Console.getChoice(stations.length);
            setDestination(stations[index - 1]);
            destination = stations[index - 1];
            System.out.println();
        }

        System.out.print("Enter travel date (Format : yyyy-mm-dd) : ");

        String date;
        try {
            date = input.readLine();
            BoardingDate = date;
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String tomorrowDay = sdf.format(date1);
            setTravelDay(tomorrowDay);
            travelDate = tomorrowDay;
            System.out.println();
        } catch (IOException | ParseException e) {
            System.out.println("Error with date");
            enquiryInputs();
        }
    }

    static int[][] seatsOnCoach;
    static int[][] totalSeats;
    static String[][][] availSeat;
    static String[][][] availSeatType;

    void listAvailableTrains() {
        JSONObject jsonObject;
        JSONArray jsonArray;
        availableTrains = listTrains();
        if (availableTrains.size() == 0) {
            System.out.println("--------No Available Trains-----------");
            System.out.println();
            UserUI.userLogged();
        } else {
            System.out.println("----------Available Trains------------");
            System.out.println();
            seatsOnCoach = new int[existingTrainsCount()][3];
            totalSeats = new int[existingTrainsCount()][3];
            availSeat = new String[existingTrainsCount()][4][4];
            availSeatType = new String[4][4][4];

            for (int trainIndex = 0; trainIndex < availableTrains.size(); trainIndex++) {

                System.out.println("--------------------------------------");
                jsonObject = (JSONObject) availableTrains.get(trainIndex);
                System.out.println("[" + (trainIndex + 1) + "] Train Name                  : " + jsonObject.get("Train_Name"));

                System.out.println("    Train Number                : " + jsonObject.get("Train_Number"));
                JSONArray coachArray = (JSONArray) jsonObject.get("Coaches");
                for (int coachIndex = 1; coachIndex <= coachArray.size(); coachIndex++) {

                    JSONObject coachObject = (JSONObject) coachArray.get(coachIndex - 1);
                    System.out.println("    Coach " + coachIndex + " Name                : " + coachObject.get("Coach_Name_" + coachIndex));
                    System.out.println("    Coach " + coachIndex + " Type                : " + coachObject.get("Coach_Type_" + coachIndex));
                    jsonArray = (JSONArray) coachObject.get("Seats_" + coachIndex);

                    int count = 0;
                    int seatCount = 0;

                    for (int seatIndex = 1; seatIndex <= jsonArray.size(); seatIndex++) {
                        JSONObject tempObject = (JSONObject) jsonArray.get(seatIndex - 1);
                        seatCount++;
                        if (tempObject.get("Seat_Status_" + seatIndex).toString().equals("Available")) {
                            availSeat[trainIndex][coachIndex - 1][count] = tempObject.get("Seat_Number_" + seatIndex).toString();
                            availSeatType[trainIndex][coachIndex - 1][count] = tempObject.get("Seat_Type_" + seatIndex).toString();
                            count++;
                        }
                    }
                    System.out.println("    Available Seats on coach " + coachIndex + "  : " + count);
                    System.out.println("    Total number of seats       : " + jsonArray.size());
                    seatsOnCoach[trainIndex][coachIndex - 1] = count;
                    totalSeats[trainIndex][coachIndex - 1] = seatCount;
                    System.out.println();
                }
            }
        }
    }

    static void printList(String[] stations) {
        for (int i = 0; i < stations.length; i++) {
            System.out.println("[" + (i + 1) + "] " + stations[i]);
        }
    }

}
