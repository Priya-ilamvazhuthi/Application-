package com.consoleApp;

public class Platform {
    private int platformNumber;
    private int platformLength;
    private int numOfSubPlatforms;
    Sub_Platform[] subPlatforms;

    public int getNumOfSubPlatforms() {
        return numOfSubPlatforms;
    }

    public void setNumOfSubPlatforms(int numOfSubPlatforms) {
        this.numOfSubPlatforms = numOfSubPlatforms;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    public int getPlatformLength() {
        return platformLength;
    }

    public void setPlatformLength(int platformLength) {
        this.platformLength = platformLength;
    }

    void createSubPlatform() {
        subPlatforms = new Sub_Platform[getNumOfSubPlatforms()];
        for (int i = 0; i < getNumOfSubPlatforms(); i++) {
            subPlatforms[i] = new Sub_Platform();
        }
    }
}
