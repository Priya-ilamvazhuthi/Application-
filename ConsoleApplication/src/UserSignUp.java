package com.consoleApp;

public class UserSignUp extends LoginSystem{
    User user = new User();

    UserSignUp(){
        System.out.println("------------Signing up User-----------");
        addUserCredentials();
        userSignUp();
    }

    void addUserCredentials() {
        user.setUserID("newuser@xyz.com");
        user.setUserPassword("NewUser123!");
    }

    void userSignUp() {
        setUserEmail(user.getUserID());
        setPassword(user.getUserPassword());
        if (!validateUserID()) {
            System.out.println("-----------Invalid username-----------");
        }
        if (!validatePassword()) {
            System.out.println("-----------Invalid Password-----------");
            System.out.println("Password must contain:");
            System.out.println(" - 8 to 15 characters");
            System.out.println(" - at least one small letter");
            System.out.println(" - at least one capital letter");
            System.out.println(" - at least one number");
            System.out.println(" - at least one special character");
            System.out.println();
        }
        if (validateUserID() && validatePassword()) {
            if (signUp())
                System.out.println("--------Signed up successfully--------");
            else {
                System.out.println("--------UserID exists already---------");
                Console.welcomePage();
            }
        }
    }
}
