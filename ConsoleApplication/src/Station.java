package com.consoleApp;

public class Station {
    private String stationName;
    private String stationCode;
    private String stationAddress;
    private int numOfPlatforms;
    Platform[] platforms;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public int getNumOfPlatforms() {
        return numOfPlatforms;
    }

    public void setNumOfPlatforms(int numOfPlatforms) {
        this.numOfPlatforms = numOfPlatforms;
    }

    void createPlatform() {
        platforms = new Platform[getNumOfPlatforms()];
        for (int i = 0; i < getNumOfPlatforms(); i++) {
            platforms[i] = new Platform();
        }
    }
}