package com.consoleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("          Welcome to Train Ticket Booking             ");
        System.out.println("======================================================");
        System.out.println("Choose one option: \n[1] Login as Admin\n[2] User Login \n[3] Signup");
        int choice = getChoice( 3);
        switch (choice) {
            case 1:
                adminLoginPage();
                break;
            case 2:
                userLoginPage();
                break;
            case 3:
                signUpPage();
                break;
            default:
                break;
        }
    }

    static void adminLoginPage() {
        Admin admin = new Admin();
        LoginSystem loginSystem = new LoginSystem();
        admin.setAdminUserID("admin@xyz.com");
        admin.setAdminPassword("Admin123!");
        if (!loginSystem.adminLogin()) {
            System.out.println("Invalid");
        } else {
            System.out.println("Admin logged in successfully");
            System.out.println("Choose one option: \n[1] Add a train\n[2] Edit a train \n[3] Cancel a train");

            int choice = getChoice( 3);

            TrainSystem trainSystem = new TrainSystem();
            switch (choice) {
                case 1 -> {
                    newTrainInputs();
                    System.out.println("Train added successfully");
                }
                case 2 -> {
                    //updateTrainInputs();
                    trainSystem.updateTrain();
                    System.out.println("Train updated successfully");
                }
                case 3 -> {
                    trainSystem.cancelTrain();
                    System.out.println("Train cancelled successfully");
                }
            }
        }

    }

    static void userLoginPage() {
        User user = new User();
        user.setUserID("newuser@xyzz.com");
        user.setUserPassword("NewUser1234!");
        LoginSystem loginSystem = new LoginSystem();
        if (!loginSystem.userLogin()) {
            System.out.println("Invalid");
        } else {
            System.out.println("User logged in successfully");
            System.out.println(
                    "Choose one option: \n[1] Enquire\n[2] Book a train \n[3] View Ticket Status\n[4] Cancel Ticket ");
            int choice = getChoice(4);
            switch (choice) {
                case 1:
                    System.out.println("Enquiry");
                    break;
                case 2:
                    System.out.println("Booking");
                    break;
                case 3:
                    System.out.println("Status");
                    break;
                case 4:
                    System.out.println("Cancel");
                    break;
                default:
                    break;
            }
        }
    }

    static void signUpPage() {
        User user = new User();
        user.setUserID("newuser@xyz.com");
        user.setUserPassword("NewUser123!");
        LoginSystem loginSystem = new LoginSystem();
        if (!loginSystem.validateUserID()) {
            System.out.println("Invalid username");
        }
        if(!loginSystem.validatePassword()){
            System.out.println("Invalid Password");
            System.out.println("Password must contain:");
            System.out.println(" - 8 to 15 characters");
            System.out.println(" - at least one small letter");
            System.out.println(" - at least one capital letter");
            System.out.println(" - at least one number");
            System.out.println(" - at least one special character");
        }
        if(loginSystem.validateUserID() && loginSystem.validatePassword()){
            if(loginSystem.signUp())
                System.out.println("Signed up successfully");
            else
                System.out.println("UserID exists already");
        }
    }

    static int getChoice(int end) {
        int start = 1;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        try {
            choice = Integer.parseInt(input.readLine());
            while (choice <= start && choice >= end){
                System.out.println("Enter a valid option:");
                choice = Integer.parseInt(input.readLine());
            }
        } catch (NumberFormatException | IOException ex) {
            System.out.println("Invalid");
        }
        return choice;
    }

    static void newTrainInputs(){
        TrainSystem trainSystem = new TrainSystem();
        trainSystem.setNumOfTrains(1);
        trainSystem.createTrain();
        trainSystem.trains[0].setTrainName("Train B");
        trainSystem.trains[0].setTrainNumber(2345);
        trainSystem.trains[0].setNumOfCoaches(2);
        trainSystem.trains[0].createCoach();
        // coach 1
        trainSystem.trains[0].coaches[0].setCoachName("2S");
        trainSystem.trains[0].coaches[0].setCoachType("Two-tier");
        trainSystem.trains[0].coaches[0].setFare(10);
        trainSystem.trains[0].coaches[0].setNumOfSeats(2);
        trainSystem.trains[0].coaches[0].createSeat();
        trainSystem.trains[0].coaches[0].seats[0].setSeatNumber(1);
        trainSystem.trains[0].coaches[0].seats[0].setSeatType("Lower");
        trainSystem.trains[0].coaches[0].seats[0].setSeatStatus("Available");
        trainSystem.trains[0].coaches[0].seats[1].setSeatNumber(2);
        trainSystem.trains[0].coaches[0].seats[1].setSeatType("Middle");
        trainSystem.trains[0].coaches[0].seats[1].setSeatStatus("Available");

        //coach 2
        trainSystem.trains[0].coaches[1].setCoachName("A1");
        trainSystem.trains[0].coaches[1].setCoachType("AC");
        trainSystem.trains[0].coaches[1].setFare(160);
        trainSystem.trains[0].coaches[1].setNumOfSeats(2);
        trainSystem.trains[0].coaches[1].createSeat();
        trainSystem.trains[0].coaches[1].seats[0].setSeatNumber(1);
        trainSystem.trains[0].coaches[1].seats[0].setSeatType("Lower");
        trainSystem.trains[0].coaches[1].seats[0].setSeatStatus("Available");
        trainSystem.trains[0].coaches[1].seats[1].setSeatNumber(2);
        trainSystem.trains[0].coaches[1].seats[1].setSeatType("Middle");
        trainSystem.trains[0].coaches[1].seats[1].setSeatStatus("Available");
        trainSystem.addTrain();
    }
}
