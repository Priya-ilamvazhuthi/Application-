package com.consoleApp;

import org.json.simple.JSONObject;

public class ViewStatusPage extends BookingSystem{
    ViewStatusPage(){
        System.out.println("----------View Ticket Status----------");
        System.out.println();
        viewStatusInputs();
        UserUI.userLogged();
    }

    void viewStatusInputs() {
        String[] bookingHistory = listBooking();
        System.out.println("-----------Booking History------------");
        System.out.println();
        for (int i = 0; i < bookingHistory.length; i++) {
            System.out.println("[" + (i + 1) + "] " + bookingHistory[i]);
        }
        System.out.println();
        int choice = Console.getChoice(bookingHistory.length);
        JSONObject obj = viewStatus(bookingHistory[choice - 1]);
        System.out.println("-----------Booking status-------------");
        System.out.println();
        Console.display(obj);
    }
}
