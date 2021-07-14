package com.consoleApp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ViewTicket extends BookingSystem{
    ViewTicket(){
        System.out.println("----------View Ticket details---------");
        System.out.println();
        viewTicketInputs();
        UserUI.userLogged();
    }

    void viewTicketInputs() {
        String[] bookingHistory = listBooking();
        System.out.println("-----------Booking history------------");
        System.out.println();
        for (int i = 0; i < bookingHistory.length; i++) {
            System.out.println("[" + (i + 1) + "] " + bookingHistory[i]);
        }
        System.out.println();
        int choice = Console.getChoice(bookingHistory.length);
        JSONObject obj = viewTicket(bookingHistory[choice - 1]);
        System.out.println("-----------Ticket details-------------");
        System.out.println();
        System.out.println("Booking ID                :   "+ obj.get("Booking_ID"));
        System.out.println("Boarding Station          :   "+ obj.get("Boarding Station"));
        System.out.println("Destination               :   "+ obj.get("Destination"));
        System.out.println("Train Name                :   "+ obj.get("Train_Name"));
        System.out.println("Train Number              :   "+ obj.get("Train_Number"));
        System.out.println("Boarding Date             :   "+ obj.get("Date"));
        System.out.println("Ticket Fare               :   "+ obj.get("Fare"));

        JSONArray passengers = (JSONArray) obj.get("Passengers");
        obj = (JSONObject) passengers.get(0);

        for ( int i =0; i < (obj.size()/6) ; i++) {
            System.out.println();
            System.out.println("Passenger "+(i+1)+" Name          :   " + obj.get("Passenger"+(i+1)+"_Name"));
            System.out.println("Passenger "+(i+1)+" Coach Name    :   " + obj.get("Passenger"+(i+1)+"_Coach_Name"));
            System.out.println("Passenger "+(i+1)+" Coach Type    :   " + obj.get("Passenger"+(i+1)+"_Coach_Type"));
            System.out.println("Passenger "+(i+1)+" Seat Number   :   " + obj.get("Passenger"+(i+1)+"_Seat_Number"));
            System.out.println("Passenger "+(i+1)+" Seat Type     :   " + obj.get("Passenger"+(i+1)+"_Seat_Type"));
            System.out.println("Passenger "+(i+1)+" Ticket status :   " + obj.get("Passenger"+(i+1)+"_Booking_Status"));
        }
        System.out.println("--------------------------------------");
        System.out.println();
    }
}