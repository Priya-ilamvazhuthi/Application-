package com.consoleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaymentPage extends BookingSystem{
    PaymentPage(){
        System.out.println("--------------Payment-----------------");
        getConfirmation();
    }
    void getConfirmation() {
        int fare = getTotalFare();
        System.out.println("Total booking fare     :   " + fare);
        System.out.println("Tax (5%)               :   " + (fare * 0.05));
        System.out.println("Total fare to be paid  :   " + (fare + (fare * 0.05)));
        System.out.println("Proceed to payment? y/n");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (input.readLine().equals("y")) {
                System.out.println("Payment successful");
                bookTicket();
            } else {
                System.out.println("Payment not successful");
                UserUI.userLogged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
