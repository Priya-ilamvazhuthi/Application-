package com.consoleApp;

public class Admin {
    public static String adminUserID;
    public static String adminPassword;
    LoginSystem loginSystem = new LoginSystem();

    public void setAdminUserID(String adminUserID) {
        loginSystem.setUserEmail(adminUserID);
        this.adminUserID = adminUserID;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}

