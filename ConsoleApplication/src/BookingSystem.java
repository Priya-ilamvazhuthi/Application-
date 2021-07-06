package com.consoleApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class BookingSystem extends TrainSystem {
    final private String ticketFilePath = "tickets.json";
    private String boardingStation;
    private String destination;
    private String boardingTrain;
    private String travelDate;
    private String boardingTrainNumber;
    private int numOfTickets;
    private int boardingTrainIndex;
    private int boardingCoachIndex;
    private int cancelledNumOfTickets = getNumOfTickets();

    Passenger[] passengers;
    Ticket ticket = new Ticket();

    public int getBoardingCoachIndex() {
        return boardingCoachIndex;
    }

    public void setBoardingCoachIndex(int boardingCoachIndex) {
        this.boardingCoachIndex = boardingCoachIndex;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getBoardingTrainNumber() {
        return boardingTrainNumber;
    }

    public void setBoardingTrainNumber(String boardingTrainNumber) {
        this.boardingTrainNumber = boardingTrainNumber;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    public String getBoardingTrain() {
        return boardingTrain;
    }

    public void setBoardingTrain(String trainName) {
        this.boardingTrain = trainName;
    }

    public int getBoardingTrainIndex() {
        return boardingTrainIndex;
    }

    public void setBoardingTrainIndex(int index) {
        this.boardingTrainIndex = index;
    }

    public String getBoardingStation() {
        return boardingStation;
    }

    public void setBoardingStation(String boardingStation) {
        this.boardingStation = boardingStation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    void createPassengers() {
        passengers = new Passenger[getNumOfTickets()];
        for (int iterator = 0; iterator < getNumOfTickets(); iterator++) {
            passengers[iterator] = new Passenger();
        }
    }

    void bookTicket() {
        generateBookingID();
        int trainIndex = 1;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject finalObject = new JSONObject();
        JSONObject passengerObject = new JSONObject();
        JSONArray passengerArray = new JSONArray();
        JSONObject ticketObject = new JSONObject();

        if ((existingTicketsCount() != 0)) {
            try {
                FileReader fileReader = new FileReader(ticketFilePath);
                jsonObject = (JSONObject) jsonParser.parse(fileReader);
                int size = existingTicketsCount();
                for (int i = 1; i <= size; i++) {
                    Object tempObject = jsonObject.get("Ticket_" + i);
                    finalObject.put("Ticket_" + trainIndex++, tempObject);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        ticketObject.put("Booking_ID", ticket.getBookingID());
        ticketObject.put("Boarding Station", getBoardingStation());
        ticketObject.put("Destination", getDestination());
        ticketObject.put("Date", getTravelDate());
        ticketObject.put("Train_Name", getBoardingTrain());
        ticketObject.put("Train_Number", getBoardingTrainNumber());

        for (int l = 0; l < getNumOfTickets(); l++) {
            passengerObject.put("Passenger" + (l + 1) + "_Name", passengers[l].getPassengerName());
            passengerObject.put("Passenger" + (l + 1) + "_Coach_Name", passengers[l].getBookedCoach());
            passengerObject.put("Passenger" + (l + 1) + "_Coach_Type", passengers[l].getBookedCoachType());

            passengerObject.put("Passenger" + (l + 1) + "_Seat_Number", passengers[l].getBookedSeat());
            passengerObject.put("Passenger" + (l + 1) + "_Seat_Type", passengers[l].getBookedSeatType());
            passengerObject.put("Passenger" + (l + 1) + "_Booking_Status", passengers[l].getBookingStatus());
        }

        passengerArray.add(passengerObject);
        ticketObject.put("Passengers", passengerArray);
        ticketObject.put("Fare", (getTotalFare() + (getTotalFare() * 0.5)));
        finalObject.put("Ticket_" + trainIndex, ticketObject);

        updateStatus();
        writeJson(finalObject, ticketFilePath);
    }

    void generateBookingID() {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        ticket.setBookingID(id);
    }

    void updateOnCancel() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject ticketObject;
        JSONArray passengerArray;
        JSONObject passengerObject;
        JSONArray jsonArray;
        for(int i = 0 ; cancelledNumOfTickets <=0 ; i++) {
            try {
                FileReader fileReader = new FileReader(ticketFilePath);
                jsonObject = (JSONObject) jsonParser.parse(fileReader);
                for (int ticketIndex = 0; ticketIndex < existingTicketsCount(); ticketIndex++) {
                    ticketObject = (JSONObject) jsonObject.get("Ticket_" + ticketIndex);
                    passengerArray = (JSONArray) ticketObject.get("Passengers");
                    passengerObject = (JSONObject) passengerArray.get(0);
                    for (int passengerIndex = 0; passengerIndex < (passengerObject.size() / 6); passengerIndex++) {
                        if(passengerObject.get("Passenger" + passengerIndex + "_BookingStatus").toString().equals("Waiting")) {
                            passengerObject.replace("Passenger" + passengerIndex + "_BookingStatus","Confirmed");
                            cancelledNumOfTickets--;
                        }
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    void updateStatus() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject trainObject;
        JSONArray jsonArray;
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            int trainIndex = getBoardingTrainIndex();
            trainObject = (JSONObject) jsonObject.get("Train_" + trainIndex);
            jsonArray = (JSONArray) trainObject.get("Coaches");
            JSONObject coachObject = (JSONObject) jsonArray.get(getBoardingCoachIndex() - 1);
            JSONArray seatArray = (JSONArray) coachObject.get("Seats_" + getBoardingCoachIndex());
            int count = 1;
            System.out.println(seatArray.size());
            for (int i = 1; i <= seatArray.size(); i++) {
                JSONObject seatObject = (JSONObject) seatArray.get(0);
                if (seatObject.get("Seat_Status_" + i).toString().equals("Available")) {
                    seatObject.replace("Seat_Status_" + i, "Booked");
                    count++;
                }
                if (count > numOfTickets)
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void cancelTicket(int ticketNumber) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            System.out.println("c  "+jsonObject);
            jsonObject.remove("Ticket_" + ticketNumber);
            JSONObject finalObject = new JSONObject();
            int ticketIndex = 1;
            for (int i = 1; i < existingTicketsCount(); i++) {
                if (ticketIndex == ticketNumber)
                    ticketIndex++;
                finalObject.put("Ticket_" + i, jsonObject.get("Ticket_" + ticketIndex++));
            }
            writeJson(finalObject,ticketFilePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String[] listBooking() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        String[] bookingList = new String[existingTicketsCount()];
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int i = 1; i <= existingTicketsCount(); i++) {
                JSONObject tempObject = (JSONObject) jsonObject.get("Ticket_" + i);
                bookingList[i - 1] = tempObject.get("Booking_ID").toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bookingList;
    }

    JSONArray listTrains() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONArray availableTrainArray = new JSONArray();
        try {
            FileReader fileReader = new FileReader("train.json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int trainIterator = 1; trainIterator <= existingTrainsCount(); trainIterator++) {
                JSONObject trains = (JSONObject) jsonObject.get("Train_" + (trainIterator));
                JSONObject operatingDays = (JSONObject) trains.get("Operating_Days");
                JSONObject route = (JSONObject) trains.get("Route");
                for (int dayIterator = 1; dayIterator < 7; dayIterator++) {
                    if (operatingDays.containsKey("Day_" + dayIterator)) {
                        if (operatingDays.get("Day_" + dayIterator).toString().equals(getTravelDate())) {
                            for (int routeIterator = 1; routeIterator <= route.size(); routeIterator++) {
                                if (route.get("Stop_" + routeIterator).toString().equals(getBoardingStation())) {
                                    for (int j = trainIterator + 1; j < route.size(); j++) {
                                        if (route.get("Stop_" + j).toString().equals(getDestination())) {
                                            availableTrainArray.add(trains);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        String[] availableTrains = new String[availableTrainArray.size()];
        for (int i = 0; i < availableTrainArray.size(); i++) {
            availableTrains[i] = availableTrainArray.get(i).toString();
        }

        return availableTrainArray;
    }

    public JSONObject viewStatus(String bookingID) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject finalObject = new JSONObject();
        JSONArray jsonArray;
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int i = 0; i < existingTicketsCount(); i++) {
                JSONObject tempObject = (JSONObject) jsonObject.get("Ticket_" + (i + 1));
                String tempObject1 = tempObject.get("Booking_ID").toString();
                if (bookingID.equals(tempObject1)) {
                    jsonArray = (JSONArray) tempObject.get("Passengers");
                    tempObject = (JSONObject) jsonArray.get(0);
                    for (int j = 0; j < (tempObject.size() / 6); j++) {
                        String status = tempObject.get("Passenger" + (j + 1) + "_Booking_Status").toString();
                        finalObject.put("Passenger_" + (j + 1), status);
                    }
                    break;
                }
            }
        } catch (FileNotFoundException | ClassCastException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalObject;
    }

    int existingTicketsCount() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
        } catch (Exception e) {
            return 0;
        }
        return jsonObject.size();
    }

    int getTotalFare() {
        return (numOfTickets * ticket.getFare());
    }

    public static void writeJson(JSONObject Object, String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Object);
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
