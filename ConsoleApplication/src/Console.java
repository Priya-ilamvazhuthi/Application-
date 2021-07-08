package com.consoleApp;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("          Welcome to Train Ticket Booking             ");
        System.out.println("======================================================");
        System.out.println();
        welcomePage();

    }

    static void welcomePage() {
        System.out.println("Choose one option: \n[1] Login as Admin\n[2] User Login \n[3] Signup\n[4] Close");
        System.out.println();
        int choice = getChoice(4);
        switch (choice) {
            case 1 -> new AdminUI();
            case 2 -> new UserUI();
            case 3 -> new UserSignUp();
            case 4 -> System.exit(0);
        }
    }

    static int getChoice(int end) {
        int choice;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            do {
                System.out.print("Enter a choice from above : ");
                choice = Integer.parseInt(input.readLine());
                System.out.println();
            } while (!(choice >= 1 && choice <= end));
        } catch (IOException | NumberFormatException ex) {
            choice = getChoice(end);
        }
        return choice;
    }

    static void display(JSONObject object) {
        for (String key : (Iterable<String>) object.keySet()) {
            System.out.println(key + " : " + object.get(key));
        }
        System.out.println("--------------------------------------");
        System.out.println();
    }
}
