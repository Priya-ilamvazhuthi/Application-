package com.consoleApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class TrainSystem {

    JSONObject seatObject = new JSONObject();
    JSONArray seatArray = new JSONArray();
    JSONObject coachObject = new JSONObject();
    JSONArray coachArray = new JSONArray();
    JSONObject trainObject = new JSONObject();
    JSONArray trainArray = new JSONArray();

    JSONObject jsonObject1 = new JSONObject();
    JSONArray jsonArray1 = new JSONArray();
    JSONParser jsonParser1 = new JSONParser();
    Object object1 = null;
    String filepath = "train.json";
    int numOfTrains;
    Train[] trains;

    public int getNumOfTrains() {
        return numOfTrains;
    }

    public void setNumOfTrains(int numOfTrains) {
        this.numOfTrains = numOfTrains;
    }

    void updateTrain() {
        try {
            FileReader reader = new FileReader("train.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject.put("Train_Name", "Train abc");
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            FileWriter writer = new FileWriter("train.json");
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void createTrain() {
        trains = new Train[numOfTrains];
        for (int i = 0; i < numOfTrains; i++) {
            trains[i] = new Train();
        }
    }

    void addTrain() {
        try {
            FileReader file = new FileReader(filepath);
            object1 = jsonParser1.parse(file);
            jsonArray1 = (JSONArray) object1;
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int trainIndex = jsonArray1.size() + 1;
        for (int i = 0; i < getNumOfTrains(); i++) {
            trainObject = new JSONObject();
            trainObject.put("Train_Name_" + trainIndex, trains[i].getTrainName());
            trainObject.put("Train_Number_" + trainIndex++, trains[i].getTrainNumber());
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
                coachObject.put("Seats", seatArray);
                coachArray.add(coachObject);
                seatArray = new JSONArray();
            }

            trainObject.put("Coaches", coachArray);
            trainArray.add(object1);
            trainArray.add(trainObject);
            coachArray = new JSONArray();
        }
        jsonObject1.put("Trains", trainArray);
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject1);
            FileWriter writer = new FileWriter("train.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void cancelTrain() {

    }
}
