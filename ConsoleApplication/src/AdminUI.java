package com.consoleApp;

public class AdminUI extends LoginSystem{
    Admin admin = new Admin();

    AdminUI(){
        System.out.println("-----------Logging in Admin-----------");
        addAdminCredentials();
        adminLogin();
    }

    void addAdminCredentials() {
        admin.setAdminUserID("admin@xyz.com");
        admin.setAdminPassword("Admin123!");
    }
    void adminLogin() {
        setUserEmail(admin.getAdminUserID());
        setPassword(admin.getAdminPassword());
        if (!isAdminLogin()) {
            System.out.println("-------Invalid login credentials------");
        } else {
            System.out.println("-----Admin logged in successfully-----");
            adminLoggedIn();
        }
    }

    static void adminLoggedIn() {
        System.out.println(
                "Choose one option: \n[1] Add a train\n[2] Edit a train \n[3] Cancel a train\n[4] Add a station\n[5] Logout");
        int choice = Console.getChoice(5);
        switch (choice) {
            case 1 -> new AddTrainUI();
            case 2 -> new UpdateTrainUI();
            case 3 -> new CancelTrainUI();
            case 4 -> new StationUI();
            case 5 -> Console.welcomePage();
        }
    }


}
