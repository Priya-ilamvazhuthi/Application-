package com.consoleApp;

public class CancelTicketUI extends BookingSystem {
    CancelTicketUI() {
        System.out.println("----------Ticket Cancellation---------");
        System.out.println();
        cancelTicketInputs();
        System.out.println("----Ticket Cancelled Successfully----");
        System.out.println();
        UserUI.userLogged();
    }
    void cancelTicketInputs() {
        String[] bookingHistory = listBooking();
        System.out.println("-----------Booking History------------");
        System.out.println();
        for (int i = 0; i < bookingHistory.length; i++) {
            System.out.println("[" + (i + 1) + "] " + bookingHistory[i]);
        }
        System.out.println();
        int choice = Console.getChoice(bookingHistory.length);
        cancelTicket(choice);
    }
}
