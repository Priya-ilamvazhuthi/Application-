package com.consoleApp;

public class AddTrainUI extends TrainSystem{

    private final String[] coachTypes = { "Two-tier", "Three-tier", "Sleeper", "AC" };
    private final String[] seatTypes = { "Lower", "Middle", "Upper", "Side Lower", "Side Upper", "Sitting" };
    private final String[] seatStatuses = { "Available", "Booked", "Blocked" };
    private final String[] operatingDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    public String[] getOperatingDays() {
        return operatingDays;
    }

    AddTrainUI() {
        System.out.println();
        System.out.println("-------------Adding Train-------------");
        System.out.println();
        addTrainInputs();
        if (addTrain()) {
            System.out.println("-------Train added successfully-------");
        } else {
            System.out.println("---------Train already exists---------");
        }
        System.out.println();
        AdminUI.adminLoggedIn();
    }

     void addTrainInputs() {

        String[] operatingDays = getOperatingDays();
        String[] Schedule = { operatingDays[0], operatingDays[1], operatingDays[2], operatingDays[3], operatingDays[4],
                operatingDays[5], operatingDays[6] };
        setNumOfTrains(1);
        createTrain();
        String[] stationNameList = listStationName();

        trains[0].setTrainName("Uzhavan Express");
        trains[0].setTrainNumber(9671);
        trains[0].route.setNumOfStops(7);
        trains[0].route.createStops();
        trains[0].route.stops[0].setStationName(stationNameList[0]);
        trains[0].route.stops[1].setStationName(stationNameList[1]);
        trains[0].route.stops[2].setStationName(stationNameList[2]);
        trains[0].route.stops[3].setStationName(stationNameList[3]);
        trains[0].route.stops[4].setStationName(stationNameList[4]);
        trains[0].route.stops[5].setStationName(stationNameList[5]);
        trains[0].route.stops[6].setStationName(stationNameList[6]);
        trains[0].schedule.setOperatingDays(Schedule);
        trains[0].setNumOfCoaches(2);
        trains[0].createCoach();
        // First Coach
        trains[0].coaches[0].setCoachName("2S");
        trains[0].coaches[0].setCoachType(coachTypes[0]);
        trains[0].coaches[0].setFare(80);
        trains[0].coaches[0].setNumOfSeats(2);
        trains[0].coaches[0].createSeat();
        trains[0].coaches[0].seats[0].setSeatNumber(1);
        trains[0].coaches[0].seats[0].setSeatType(seatTypes[5]);
        trains[0].coaches[0].seats[0].setSeatStatus(seatStatuses[0]);
        trains[0].coaches[0].seats[1].setSeatNumber(2);
        trains[0].coaches[0].seats[1].setSeatType(seatTypes[5]);
        trains[0].coaches[0].seats[1].setSeatStatus(seatStatuses[0]);
        // Second Coach
        trains[0].coaches[1].setCoachName("3S");
        trains[0].coaches[1].setCoachType(coachTypes[1]);
        trains[0].coaches[1].setFare(90);
        trains[0].coaches[1].setNumOfSeats(1);
        trains[0].coaches[1].createSeat();
        trains[0].coaches[1].seats[0].setSeatNumber(1);
        trains[0].coaches[1].seats[0].setSeatType(seatTypes[5]);
        trains[0].coaches[1].seats[0].setSeatStatus(seatStatuses[0]);
    }
}
