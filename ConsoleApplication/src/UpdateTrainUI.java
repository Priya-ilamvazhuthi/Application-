package com.consoleApp;

public class UpdateTrainUI extends TrainSystem {
    private final String[] coachTypes = { "Two-tier", "Three-tier", "Sleeper", "AC" };
    static String[] seatTypes = { "Lower", "Middle", "Upper", "Side Lower", "Side Upper", "Sitting" };
    private final String[] seatStatuses = { "Available", "Booked", "Blocked" };
    private final String[] operatingDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };


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
        String newTrainName = "Chennai express";
        String newTrainNumber = "8765";
        String newCoachName = "S1";
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
            case 1 -> updateTrain(trainIndex, "Train_Name", newTrainName);
            case 2 -> updateTrain(trainIndex, "Train_Number", newTrainNumber);
            case 3 -> {
                int coachLength = coachLength(trainIndex);
                System.out.println("Enter the parameter to be modified");
                for (int i = 0; i < coachLength; i++) {
                    System.out.println("[" + (i + 1) + "] " + "Coach " + (i + 1));
                }
                coachIndex = Console.getChoice(coachLength);
                System.out.println("Enter the parameter to be modified");
                for (int i = 0; i < coachToModify.length; i++) {
                    System.out.println("[" + (i + 1) + "] " + coachToModify[i]);
                }
                int coachKeys = Console.getChoice(coachToModify.length);
                switch (coachKeys) {
                    case 1 -> updateTrain("Train_" + trainIndex, "Coach_Name_" + coachIndex, newCoachName);
                    case 2 -> updateTrain("Train_" + trainIndex, "Coach_Type_" + coachIndex, newCoachType);
                    case 3 -> updateTrain("Train_" + trainIndex, "Coach_Fare_" + coachIndex, newCoachFare);
                    case 4 -> {
                        int seatLength = seatLength(trainIndex, coachIndex);
                        System.out.println("Enter the parameter to be modified");
                        for (int i = 0; i < seatLength; i++) {
                            System.out.println("[" + (i + 1) + "] " + "Seat " + (i + 1));
                        }
                        seatIndex = Console.getChoice(seatLength);
                        System.out.println("Enter the parameter to be modified");
                        for (int i = 0; i < seatToModify.length; i++) {
                            System.out.println("[" + (i + 1) + "] " + seatToModify[i]);
                        }
                        int seatKeys = Console.getChoice(seatToModify.length);
                        switch (seatKeys) {
                            case 1 -> updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                    "Seat_Number_" + seatIndex, newSeatNumber);
                            case 2 -> updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                    "Seat_Type_" + seatIndex, newSeatType);
                            case 3 -> updateTrain("Train_" + trainIndex, "Seats_" + coachIndex,
                                    "Seat_Status_" + seatIndex, newSeatStatus);
                        }
                        System.out.println("Seat " + seatIndex);
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
