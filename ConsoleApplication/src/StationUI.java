package com.consoleApp;

public class StationUI extends TrainSystem {
    final String[] platformType = { "Ladies", "Unreserved", "Disabled", "General" };

    StationUI() {
        System.out.println("-----------Adding Station-------------");
        System.out.println();
        stationAddInputs();
        if (addStation()) {
            System.out.println("------Station added successfully------");
        } else {
            System.out.println("--------Station already exists--------");
        }
        AdminUI.adminLoggedIn();
    }
    void stationAddInputs() {
        setNumOfStations(1);
        createStation();
        stations[0].setStationName("Coimbatore junction");
        stations[0].setStationCode("CBE");
        stations[0].setStationAddress("Coimbatore");
        stations[0].setNumOfPlatforms(2);
        stations[0].createPlatform();
        stations[0].platforms[0].setPlatformNumber(1);
        stations[0].platforms[0].setPlatformLength(1);
        stations[0].platforms[0].setNumOfSubPlatforms(2);
        stations[0].platforms[0].createSubPlatform();
        stations[0].platforms[0].subPlatforms[0].setSubPlatformNumber(1);
        stations[0].platforms[0].subPlatforms[0].setSubPlatformType(platformType[1]);
        stations[0].platforms[0].subPlatforms[1].setSubPlatformNumber(2);
        stations[0].platforms[0].subPlatforms[1].setSubPlatformType(platformType[3]);
        stations[0].platforms[1].setPlatformNumber(1);
        stations[0].platforms[1].setPlatformLength(1);
        stations[0].platforms[1].setNumOfSubPlatforms(1);
        stations[0].platforms[1].createSubPlatform();
        stations[0].platforms[1].subPlatforms[0].setSubPlatformNumber(1);
        stations[0].platforms[1].subPlatforms[0].setSubPlatformType(platformType[3]);
    }
}
