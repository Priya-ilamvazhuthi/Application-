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
        String[] stations = listStationName();
        System.out.println("-----------Boarding Station-----------");
        System.out.println();
            System.out.print("Search boarding station Name : ");
            try {
                source = input.readLine();
                source = source.substring(0, 1).toUpperCase() + source.substring(1);
                int count = 1;
                for (String station : stations) {
                    if (station.startsWith(source)) {
                        System.out.println("[" + count + "] " + station);
                        count++;
                    }
                }
                if(count == 1){
                    System.out.println("No stations found");
                    enquiryInputs();
                }
                int choice = Console.getChoice(count);
                count =1;
                for (int i =0; i< stations.length ; i ++) {
                    if (stations[i].startsWith(source)) {
                        if (count == choice) {
                            setBoardingStation(stations[i]);
                            source = stations[i];
                        }
                        count++;
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("---------Destination station----------");
        System.out.println();
        System.out.print("Search destination station Name : ");
        try {
            destination = input.readLine();
            destination = destination.substring(0, 1).toUpperCase() + destination.substring(1);
            int count = 1;
            for (String station : stations) {
                if (station.startsWith(destination)) {
                    System.out.println("[" + count + "] " + station);
                    count++;
                }
            }
            if(count == 1){
                System.out.println("No stations found");
                enquiryInputs();
            }
            int choice = Console.getChoice(count);
            count =1;
            for (int i =0 ;i < stations.length ;i++) {
                if (stations[i].startsWith(destination)) {
                    if (count == choice) {
                        setDestination(stations[i]);
                        destination = stations[i];
                    }
                    count++;
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Enter travel date (Format : yyyy-mm-dd) : ");

        String date;
        try {
            date = input.readLine();
            BoardingDate = date;
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            Date current = new Date();

            if(date1.before(current)){
                System.out.println("Booking allowed from tomorrow only");
                System.out.println("Try again");
                enquiryInputs();
            }
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                String tomorrowDay = sdf.format(date1);
                setTravelDay(tomorrowDay);
                travelDate = tomorrowDay;
                System.out.println();
            }
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
                    int flag = 0;

                    for (int seatIndex = 1; seatIndex <= jsonArray.size(); seatIndex++) {
                        JSONObject tempObject = (JSONObject) jsonArray.get(seatIndex - 1);
                        JSONArray tempArray = (JSONArray) tempObject.get("Seat_Status");
                        seatCount++;
                        if (!(tempArray.size() == 0)) {
                            for (Object o : tempArray) {
                                JSONObject tempObject1 = (JSONObject) o;
                                if (tempObject1.get("Date").toString().equals(BoardingDate)) {
                                    String[] scheduleList = listSchedule(trainIndex + 1);

                                    for (int i = 0; i < scheduleList.length; i++) {
                                        if (scheduleList[i].equals(tempObject1.get("Destination"))) {
                                            for (int j = i + 1; j < scheduleList.length; j++) {
                                                if ((scheduleList[j].equals(getDestination()))) {
                                                    flag = 0;
                                                    break;
                                                } else flag = 1;
                                            }
                                            if (flag == 1) {
                                                availSeat[trainIndex][coachIndex - 1][count] = null;
                                                availSeatType[trainIndex][coachIndex - 1][count] = null;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else flag = 0;
                        if (flag == 0) {
                            //System.out.println("here too");
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

}
