package com.consoleApp;

public class Train {
    String trainName ;
    int trainNumber;
    int numOfCoaches;
    Route route = new Route();
    Schedule schedule = new Schedule();
    Coach[] coaches;


    void createCoach(){
        coaches = new Coach[numOfCoaches];
        for(int i =0; i<numOfCoaches;i++){
            coaches[i]= new Coach();

        }
    }
    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getNumOfCoaches() {
        return numOfCoaches;
    }

    public void setNumOfCoaches(int numOfCoaches) {
        this.numOfCoaches = numOfCoaches;
    }


}
