package com.consoleApp;

public class User {
    private String userID;
    private String userPassword;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
