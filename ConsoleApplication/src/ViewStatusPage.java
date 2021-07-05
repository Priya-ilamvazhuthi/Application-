package com.consoleApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONObject;

public class ViewStatusPage extends BookingSystem{
    ViewStatusPage(){
        System.out.println("----------View Ticket Status----------");
        System.out.println();
        viewStatusInputs();
    }

    void viewStatusInputs() {
        String[] bookingHistory = listBooking();
        for (int i = 0; i < bookingHistory.length; i++) {
            System.out.println("[" + (i + 1) + "] " + bookingHistory[i]);
        }
        int choice = Console.getChoice(bookingHistory.length);
        JSONObject obj = viewStatus(bookingHistory[choice - 1]);
        System.out.println("-----------Booking status-------------");
        System.out.println();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
