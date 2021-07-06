package com.consoleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaymentPage extends BookingSystem{
    PaymentPage(){
        System.out.println("--------------Payment-----------------");
        System.out.println();
    }

    static boolean getConfirmation(int fare) {
        System.out.println();
        System.out.println("Total booking fare     :   " + fare);
        System.out.println("Tax (5%)               :   " + (fare * 0.05));
        System.out.println("Total fare to be paid  :   " + (fare + (fare * 0.05)));
        System.out.print("Proceed to payment? y/n :");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (input.readLine().equals("y")) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
