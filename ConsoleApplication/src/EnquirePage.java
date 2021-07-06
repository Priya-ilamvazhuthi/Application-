package com.consoleApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EnquirePage extends BookingSystem{
    static JSONArray availableTrains;
    static String[] json;

    void enquiryInputs() {
        System.out.println("---------------Enquiry----------------");
        System.out.println();
        int index;
        String[] stations = listStationName();

        // Choose a station from station list as source
        System.out.println("Choose the boarding station");
        printList(stations);
        index = Console.getChoice( stations.length );
        setBoardingStation(stations[index - 1]);
        System.out.println();

        // Choose a station from station list as destination
        System.out.println("Choose the destination station");
        printList(stations);
        index = Console.getChoice( stations.length );
        setDestination(stations[index - 1]);
        System.out.println();

        // Choose a travel date (Only tomorrow as in tatkal)
        System.out.println("Choose the travel date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrow_date = sdf.format(calender.getTime());

        sdf = new SimpleDateFormat("EEEE");
        calender = Calendar.getInstance();
        calender.add(Calendar.DAY_OF_MONTH,1);
        String tomorrowDay = sdf.format(calender.getTime());
        System.out.println("[1] " + tomorrow_date);

        index = Console.getChoice(1);

        if (index == 1)
            setTravelDate(tomorrowDay);

        if(listAvailableTrains()) {
            System.out.println("----------Available Trains------------");
            printList(json);
        }
        else {
            System.out.println("----------No Trains available---------");
            UserUI.userLogged();
        }
        System.out.println();
    }

    boolean listAvailableTrains() {
        availableTrains = listTrains();
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (availableTrains.size() == 0) {
                return false;
            } else {
                json = new String[availableTrains.size()];
                for (int i = 0; i < availableTrains.size(); i++) {
                    Object ob = availableTrains.get(i);
                    json[i] = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }
    static void printList(String[] stations) {
        for (int i = 0; i < stations.length; i++) {
            System.out.println("[" + (i + 1) + "] " + stations[i]);
        }
    }

}
