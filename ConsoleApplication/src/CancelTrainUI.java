package com.consoleApp;

public class CancelTrainUI extends TrainSystem {
    CancelTrainUI(){
        System.out.println("-----------Cancelling Train-----------");
        System.out.println();
        cancelTrainInputs();
        System.out.println("------Train updated Successfully------");
        System.out.println();
        AdminUI.adminLoggedIn();
    }
    void cancelTrainInputs() {
        String[] trainNameList = listTrainName();
        System.out.println("Choose the Train to be cancelled from below list");
        System.out.println();
        for (int i = 0; i < trainNameList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + trainNameList[i]);
        }
        System.out.println();
        int trainIndex = Console.getChoice(trainNameList.length);
        cancelTrain(trainIndex);
    }
}
