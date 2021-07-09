package com.consoleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateTrainUI extends TrainSystem {
    private final String[] coachTypes = { "Two-tier", "Three-tier", "Sleeper", "AC" };
    static String[] seatTypes = { "Lower", "Middle", "Upper", "Side Lower", "Side Upper", "Sitting" };
    private final String[] seatStatuses = { "Available", "Booked", "Blocked" };
    private final String[] operatingDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    UpdateTrainUI() {
        System.out.println("------------Updating Train------------");
        System.out.println();
        updateTrainInputs();
        System.out.println("------Train updated Successfully------");
        System.out.println();
        AdminUI.adminLoggedIn();
    }

    void updateTrainInputs() {
        int coachIndex, seatIndex;
        String newTrainName = "";
        String newTrainNumber = "";
        String newCoachName = "";
        String newCoachType = coachTypes[3];
        String newCoachFare = "175";
        String newSeatNumber = "5";
        String newSeatType = seatTypes[4];
        String newSeatStatus = seatStatuses[2];

        String[] toBeModified = { "Train_Name", "Train_Number", "Coaches", "Route", "Operating_Days" };
        String[] coachToModify = { "Coach_Name", "Coach_Type", "Fare", "Seats" };
        String[] seatToModify = { "Seat_Number", "Seat_Type", "Seat_Status" };

        String[] trainNameList = listTrainName();
        String[] stationNameList = listStationName();

        System.out.println("Choose the Train to be modified from below list");
        for (int i = 0; i < trainNameList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + trainNameList[i]);
        }
        int trainIndex = Console.getChoice(trainNameList.length);

        System.out.println("Enter the parameter to be modified");
        for (int i = 0; i < toBeModified.length; i++) {
            System.out.println("[" + (i + 1) + "] " + toBeModified[i]);
        }

        int trainKeys = Console.getChoice(toBeModified.length);
        switch (trainKeys) {
            case 1 -> {
                System.out.print("Enter the new Train Name :");
                try {
                    newTrainName = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTrain(trainIndex, "Train_Name", newTrainName);
                System.out.println();
            }
            case 2 -> {
                System.out.print("Enter the new Train Number :");
                try {
                    newTrainNumber = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTrain(trainIndex, "Train_Number", newTrainNumber);
                System.out.println();
            }
            case 3 -> {
                int coachLength = coachLength(trainIndex);
                System.out.println("Choose the parameter to be modified");
                System.out.println();
                for (int i = 0; i < coachLength; i++) {
                    System.out.println("[" + (i + 1) + "] " + "Coach " + (i + 1));
                }
                System.out.println();
                coachIndex = Console.getChoice(coachLength);
                System.out.println();
                for (int i = 0; i < coachToModify.length; i++) {
                    System.out.println("[" + (i + 1) + "] " + coachToModify[i]);
                }
                int coachKeys = Console.getChoice(coachToModify.length);
                switch (coachKeys) {
                    case 1 -> {
                        System.out.print("Enter the new Coach Name :");
                        try {
                            newCoachName = input.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updateTrain("Train_" + trainIndex, "Coach_Name_" + coachIndex, newCoachName);
                        System.out.println();
                    }
                    case 2 -> {
                        System.out.print("Choose the new Coach type :");
                        for (int i = 0; i < coachTypes.length; i++) {
                            System.out.println("[" + (i + 1) + "] " + coachTypes[i]);
                        }
                        int choice = Console.getChoice(coachTypes.length);
                        newCoachType = coachTypes[choice-1];
                        updateTrain("Train_" + trainIndex, "Coach_Type_" + coachIndex, newCoachType);
                        System.out.println();
                    }
                    case 3 -> {
                        System.out.print("Enter the new Coach Fare :");
                        try {
                            newCoachFare = input.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updateTrain("Train_" + trainIndex, "Coach_Fare_" + coachIndex, newCoachFare);
                        System.out.println();
                    }
                    case 4 -> {
                        int seatLength = seatLength(trainIndex, coachIndex);
                        System.out.println("Enter the parameter to be modified");
                        for (int i = 0; i < seatLength; i++) {
                            System.out.println("[" + (i + 1) + "] " + "Seat " + (i + 1));
                        }
                        seatIndex = Console.getChoice(seatLength);
                        System.out.println();
                        for (int i = 0; i < seatToModify.length; i++) {
                            System.out.println("[" + (i + 1) + "] " + seatToModify[i]);
                        }
                        int seatKeys = Console.getChoice(seatToModify.length);
                        switch (seatKeys) {
                            case 1 -> {
                                System.out.println("Enter the new Seat Number :");
                                try {
                                    newSeatNumber = input.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                        "Seat_Number_" + seatIndex, newSeatNumber);
                                System.out.println();
                            }
                            case 2 -> {
                                System.out.println("Choose the new Seat type :");
                                for (int i = 0; i < seatTypes.length; i++) {
                                    System.out.println("[" + (i + 1) + "] " + seatTypes[i]);
                                }
                                int choice = Console.getChoice(seatTypes.length);
                                newSeatType = seatTypes[choice-1];
                                updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                        "Seat_Type_" + seatIndex, newSeatType);
                                System.out.println();
                            }
                            case 3 -> {
                                System.out.println("Choose the new Seat status :");
                                for (int i = 0; i < seatStatuses.length; i++) {
                                    System.out.println("[" + (i + 1) + "] " + seatStatuses[i]);
                                }
                                int choice = Console.getChoice(seatStatuses.length);
                                newSeatStatus = seatStatuses[choice-1];
                                updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                        "Seat_Status_" + seatIndex, newSeatStatus);
                                System.out.println();
                            }
                        }
                    }
                }
            }
            case 4 -> {
                String[] newRoute = { stationNameList[0], stationNameList[1], stationNameList[2], stationNameList[3],
                        stationNameList[4], stationNameList[5], stationNameList[6] };
                updateTrain(trainIndex, "Route", newRoute);
            }
            case 5 -> {
                String[] newSchedule = { operatingDays[0], operatingDays[1], operatingDays[2], operatingDays[3],
                        operatingDays[4], operatingDays[5], operatingDays[6] };
                updateTrain(trainIndex, "Operating_Days", newSchedule);
            }
        }
    }
}
