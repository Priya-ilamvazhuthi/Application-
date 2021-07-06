package com.consoleApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

abstract class TrainSystem {
    Train[] trains;
    Station[] stations;
    private int numOfTrains;
    private int numOfStations;
    static final String filePath = "train.json";

    public int getNumOfTrains() {
        return numOfTrains;
    }

    public void setNumOfTrains(int numOfTrains) {
        this.numOfTrains = numOfTrains;
    }

    public int getNumOfStations() {
        return numOfStations;
    }

    public void setNumOfStations(int numOfStations) {
        this.numOfStations = numOfStations;
    }

    void createTrain() {
        trains = new Train[getNumOfTrains()];
        for (int i = 0; i < getNumOfTrains(); i++) {
            trains[i] = new Train();
        }
    }

    void createStation() {
        stations = new Station[getNumOfStations()];
        for (int i = 0; i < getNumOfStations(); i++) {
            stations[i] = new Station();
        }
    }

    String[] listTrainName() {
        String[] trainList = new String[existingTrainsCount()];
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int iterator = 1; iterator <= existingTrainsCount(); iterator++) {
                JSONObject tempObject;
                tempObject = (JSONObject) jsonObject.get("Train_" + iterator);
                trainList[iterator - 1] = tempObject.get("Train_Name").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainList;
    }

    String[] listStationName() {
        String[] trainList = new String[existingStationCount()];
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("stations.json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int iterator = 1; iterator <= existingStationCount(); iterator++) {
                JSONObject tempObject;
                tempObject = (JSONObject) jsonObject.get("Station_" + iterator);
                trainList[iterator - 1] = tempObject.get("Station_Name").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainList;
    }

    String[] listSchedule(int trainIndex) {
        String[] trainList = {};
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONObject trains = (JSONObject) jsonObject.get("Train_" + trainIndex);
            JSONObject route = (JSONObject) trains.get("Route");
            trainList = new String[(route.size())];
            for (int iterator = 1; iterator <= route.size(); iterator++) {
                trainList[iterator - 1] = route.get("Stop_" + iterator).toString();
            }
        } catch (NullPointerException e) {
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return trainList;
    }

    int coachLength(int index) {
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject = (JSONObject) jsonObject.get("Train_" + index);
            jsonArray = (JSONArray) jsonObject.get("Coaches");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.size();
    }

    int seatLength(int trainIndex, int coachIndex) {
        int size = 0;
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject trains = (JSONObject) jsonObject.get("Train_" + trainIndex);
            JSONArray tempArray = (JSONArray) trains.get("Coaches");

            JSONObject coaches = (JSONObject) tempArray.get(coachIndex - 1);
            JSONArray jsonArray = (JSONArray) coaches.get("Seats_" + coachIndex);
            size = jsonArray.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    void updateTrain(int trainNumber, String trainKey, String changeValue) {
        JSONObject jsonObject;
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject tempObject = (JSONObject) jsonObject.get("Train_" + trainNumber);
            tempObject.replace(trainKey, changeValue);
            writeJson(jsonObject, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateTrain(int trainNumber, String trainKey, String[] array) {
        JSONObject jsonObject;
        String key = "";
        if (trainKey.equals("Route"))
            key = "Stop_";
        if (trainKey.equals("OperatingDays"))
            key = "Day_";
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject tempObject = (JSONObject) jsonObject.get("Train_" + trainNumber);
            JSONObject tempObject1 = new JSONObject();
            for (int i = 0; i < array.length; i++) {
                tempObject1.put(key + (i + 1), array[i]);
            }
            tempObject.replace(trainKey, tempObject1);
            writeJson(jsonObject, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateTrain(String trainNumber, String coachKey, String newValue) {
        JSONObject jsonObject;
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject newObject = (JSONObject) jsonObject.get(trainNumber);
            JSONArray tempArray = (JSONArray) newObject.get("Coaches");
            JSONObject coaches = (JSONObject) tempArray.get(0);
            coaches.replace(coachKey, newValue);
            writeJson(jsonObject, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateTrain(String trainNumber, String seatNumber, String seatKey, String newValue) {
        JSONObject jsonObject;
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject newObject = (JSONObject) jsonObject.get(trainNumber);
            JSONArray tempArray = (JSONArray) newObject.get("Coaches");
            JSONObject coaches = (JSONObject) tempArray.get(0);
            JSONArray seatArr = (JSONArray) coaches.get(seatNumber);
            JSONObject seats = (JSONObject) seatArr.get(0);
            seats.replace(seatKey, newValue);
            writeJson(jsonObject, filePath);
        } catch (Exception e) {
        }
    }

    void cancelTrain(int trainIndex) {
        JSONObject jsonObject;
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject.remove("Train_" + trainIndex);
            writeJson(jsonObject, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean trainExists(String trainName) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONObject tempObject;
            for (int i = 1; i <= existingTrainsCount(); i++) {
                tempObject = (JSONObject) jsonObject.get("Train_" + i);
                if (tempObject.get("Train_Name").toString().equals(trainName))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean stationExists(String stationName) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {

            FileReader fileReader = new FileReader("stations.json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONObject tempObject;
            for (int i = 1; i <= existingStationCount(); i++) {
                tempObject = (JSONObject) jsonObject.get("Station_" + i);
                if (tempObject.get("Station_Name").toString().equals(stationName))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean addStation() {
        int flag = 0;
        for (int i = 0; i < getNumOfStations(); i++) {
            if (stationExists(stations[i].getStationName())) {
                flag = 1;
                break;
            }
        }

        if (flag == 0) {
            stationJson();
            return true;
        }
        return false;
    }

    boolean addTrain() {
        int flag = 0;
        for (int i = 0; i < getNumOfTrains(); i++) {
            if (trainExists(trains[i].getTrainName())) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            TrainJson();
            return true;
        }
        return false;
    }

    int existingTrainsCount() {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.size();
    }

    int existingStationCount() {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("stations.json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.size();
    }

    static void writeJson(JSONObject finalObject, String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalObject);
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void TrainJson() {
        JSONObject jsonObject;
        JSONObject trainObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject finalObject = new JSONObject();
        JSONObject seatObject;
        JSONArray seatArray = new JSONArray();
        JSONObject coachObject;
        JSONArray coachArray = new JSONArray();

        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONObject tempObject;
            int size = jsonObject.size();
            for (int i = 1; i <= size; i++) {
                tempObject = (JSONObject) jsonObject.get("Train_" + i);
                finalObject.put("Train_" + i, tempObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < getNumOfTrains(); i++) {
            trainObject = new JSONObject();
            trainObject.put("Train_Name", trains[i].getTrainName());
            trainObject.put("Train_Number", trains[i].getTrainNumber());
            JSONArray routeArray = new JSONArray();
            JSONObject routeObject = new JSONObject();
            int routeIndex = 1;
            for (int l = 0; l < trains[i].route.getNumOfStops(); l++) {
                routeObject.put("Stop_" + routeIndex++, trains[i].route.stops[l].getStationName());
            }
            routeArray.add(routeObject);
            trainObject.put("Route", routeArray);

            JSONObject scheduleObject = new JSONObject();
            for (int m = 0; m < trains[0].schedule.operatingDays.length; m++) {
                scheduleObject.put("Day_" + (m + 1), trains[0].schedule.operatingDays[m]);
            }
            trainObject.put("Operating_Days", scheduleObject);

            int coachIndex = 1;
            for (int j = 0; j < trains[i].getNumOfCoaches(); j++) {
                coachObject = new JSONObject();
                coachObject.put("Coach_Name_" + coachIndex, trains[i].coaches[j].getCoachName());
                coachObject.put("Coach_Type_" + coachIndex, trains[i].coaches[j].getCoachType());
                coachObject.put("Coach_Fare_" + coachIndex++, trains[i].coaches[j].getFare());
                int seatIndex = 1;
                for (int k = 0; k < trains[i].coaches[j].getNumOfSeats(); k++) {
                    seatObject = new JSONObject();
                    seatObject.put("Seat_Number_" + seatIndex, trains[i].coaches[j].seats[k].getSeatNumber());
                    seatObject.put("Seat_Type_" + seatIndex, trains[i].coaches[j].seats[k].getSeatType());
                    seatObject.put("Seat_Status_" + seatIndex++, trains[i].coaches[j].seats[k].getSeatStatus());
                    seatArray.add(seatObject);
                }
                coachObject.put("Seats_" + (j + 1), seatArray);
                coachArray.add(coachObject);
                seatArray = new JSONArray();
            }
            trainObject.put("Coaches", coachArray);
            coachArray = new JSONArray();
            finalObject.put("Train_" + (existingTrainsCount() + 1), trainObject);

        }
        writeJson(finalObject, filePath);
    }

    void stationJson() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject finalObject = new JSONObject();
        JSONObject stationObject;
        JSONObject platformObject;
        JSONArray platformArray = new JSONArray();
        JSONObject subPlatformObject;
        JSONArray subPlatformArray = new JSONArray();

        try {
            FileReader fileReader = new FileReader("stations.json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONObject tempObject;
            int size = jsonObject.size();
            for (int i = 1; i <= size; i++) {
                tempObject = (JSONObject) jsonObject.get("Station_" + i);
                finalObject.put("Station_" + i, tempObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < getNumOfStations(); i++) {
            stationObject = new JSONObject();
            stationObject.put("Station_Name", stations[i].getStationName());
            stationObject.put("Station_Code", stations[i].getStationCode());
            stationObject.put("Station_Address", stations[i].getStationAddress());
            int platformIndex = 1;
            for (int j = 0; j < stations[i].getNumOfPlatforms(); j++) {
                platformObject = new JSONObject();
                platformObject.put("Platform_Number_" + platformIndex, stations[i].platforms[j].getPlatformNumber());
                platformObject.put("Platform_Length_" + platformIndex, stations[i].platforms[j].getPlatformLength());
                int subPlatformIndex = 1;
                for (int k = 0; k < stations[i].platforms[j].getNumOfSubPlatforms(); k++) {
                    subPlatformObject = new JSONObject();
                    subPlatformObject.put("Sub_Platform_Number_" + subPlatformIndex,
                            stations[i].platforms[j].subPlatforms[k].getSubPlatformNumber());
                    subPlatformObject.put("Sub_Platform_Type_" + subPlatformIndex,
                            stations[i].platforms[j].subPlatforms[k].getSubPlatformType());
                    subPlatformArray.add(subPlatformObject);
                }
                platformObject.put("Sub_Platform_" + (j + 1), subPlatformArray);
                platformArray.add(platformObject);
                subPlatformArray = new JSONArray();
            }
            stationObject.put("Platforms", platformArray);
            platformArray = new JSONArray();
            finalObject.put("Station_" + (existingStationCount() + 1), stationObject);
        }
        writeJson(finalObject, "stations.json");
    }

}
