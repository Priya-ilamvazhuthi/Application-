package com.consoleApp;

public class Route {
    private int numOfStops;
    Station[] stops = new Station[numOfStops];

    void createStops() {
        stops = new Station[getNumOfStops()];
        for (int i = 0; i < getNumOfStops(); i++) {
            stops[i] = new Station();
        }
    }

    public int getNumOfStops() {
        return numOfStops;
    }

    public void setNumOfStops(int numOfStops) {
        this.numOfStops = numOfStops;
    }
}
