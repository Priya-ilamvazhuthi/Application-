package com.consoleApp;

public class UserUI extends LoginSystem {
    User user = new User();

    UserUI(){
        System.out.println("------------Logging in User-----------");
        addUserCredentials();
        userLogin();
    }

    void addUserCredentials(){
        user.setUserID("newuser@xyzz.com");
        user.setUserPassword("NewUser1234!");
    }

    void userLogin() {
        setUserEmail(user.getUserID());
        setPassword(user.getUserPassword());
        if (!isUserLogin()) {
            System.out.println("Invalid");
        } else {
            System.out.println("------User logged in successfully-----");
            userLogged();
        }
    }

    static void userLogged() {
        System.out.println(
                "Choose one option: \n[1] Enquire\n[2] View Schedule\n[3] Book a train \n[4] View Ticket Status\n[5] Cancel Ticket\n[6] Logout ");
        System.out.println();
        int choice = Console.getChoice(6);
        switch (choice) {
            case 1 -> new EnquirePage();
            case 2 -> new ViewSchedulePage();
            case 3 -> {
                new EnquirePage();
                new BookingPage();
            }
            case 4 -> new ViewStatusPage();
            case 5 -> new CancelTicketUI();
            case 6 -> Console.welcomePage();
        }
    }
}