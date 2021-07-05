package com.consoleApp;

public class ViewSchedulePage extends TrainSystem{

    ViewSchedulePage(){
        System.out.println("---------------Schedule---------------");
        System.out.println();
        scheduleInputs();
        UserUI.userLogged();
    }
    void scheduleInputs() {
        String[] trainNameList = listTrainName();
        System.out.println("Choose the Train ");
        for (int i = 0; i < trainNameList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + trainNameList[i]);
        }
        System.out.println();
        int trainIndex = Console.getChoice(trainNameList.length);
        if (trainIndex != 0) {
            String[] scheduleList = listSchedule(trainIndex);
            System.out.println();
            for (String s : scheduleList) {
                System.out.println(" ==> " + s);
            }
            System.out.println();
        }
    }
}
