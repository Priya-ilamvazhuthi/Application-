package com.consoleApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BookingSystem extends TrainSystem {
    final private String ticketFilePath = "tickets.json";
    private String boardingStation;
    private String destination;
    private String boardingTrain;
    static String BoardingDate;
    private String travelDay;
    private String boardingTrainNumber;
    private int numOfTickets;
    private int boardingTrainIndex;
    private int boardingCoachIndex;

    Passenger[] passengers;
    Ticket ticket = new Ticket();

    public int getBoardingCoachIndex() {
        return boardingCoachIndex;
    }

    public void setBoardingCoachIndex(int boardingCoachIndex) {
        this.boardingCoachIndex = boardingCoachIndex;
    }

    public String getTravelDay() {
        return travelDay;
    }

    public void setTravelDay(String travelDay) {
        this.travelDay = travelDay;
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
        ticketObject.put("Date", BoardingDate);
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

    void updateOnCancel(int cancelledNumOfTickets) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        JSONObject ticketObject;
        JSONArray passengerArray;
        JSONObject passengerObject;

        try {
            while (cancelledNumOfTickets > 0) {
                int temp = cancelledNumOfTickets;
                for (int i = 0; i < temp; i++) {
                    FileReader fileReader = new FileReader(ticketFilePath);
                    jsonObject = (JSONObject) jsonParser.parse(fileReader);
                    if (jsonObject.size() > 0) {
                        for (int ticketIndex = 1; ticketIndex <= existingTicketsCount(); ticketIndex++) {
                            ticketObject = (JSONObject) jsonObject.get("Ticket_" + ticketIndex);
                            if (ticketObject.get("Train_Name").toString().equals(cancelledTrain)) {
                                passengerArray = (JSONArray) ticketObject.get("Passengers");
                                passengerObject = (JSONObject) passengerArray.get(0);
                                for (int seatIndex = 1; seatIndex <= (passengerObject.size() / 6); seatIndex++) {
                                    if (passengerObject.get("Passenger" + seatIndex + "_Coach_Name").equals(cancelledCoach)) {
                                        if (passengerObject.get("Passenger" + seatIndex + "_Booking_Status")
                                                .equals("Waiting")) {
                                            passengerObject.replace("Passenger" + seatIndex + "_Booking_Status", "Confirmed");
                                            passengerObject.replace("Passenger" + seatIndex + "_Seat_Number", cancelledSeatNumber);
                                            passengerObject.replace("Passenger" + seatIndex + "_Seat_Type", cancelledSeatType);
                                        }
                                    }
                                }
                            }
                        }
                        writeJson(jsonObject, ticketFilePath);
                    }
                }

                if (cancelledNumOfTickets > 0) {

                    FileReader fileReader = new FileReader(filePath);
                    jsonObject = (JSONObject) jsonParser.parse(fileReader);
                    for (int trainIndex = 1; trainIndex <= existingTrainsCount(); trainIndex++) {
                        JSONObject trainObject = (JSONObject) jsonObject.get("Train_" + trainIndex);
                        if (trainObject.get("Train_Name").toString().equals(cancelledTrain)) {
                            JSONArray coachArray = (JSONArray) trainObject.get("Coaches");
                            for (int coachIndex = 1; coachIndex <= coachArray.size(); coachIndex++) {
                                JSONObject coachObject = (JSONObject) coachArray.get((coachIndex - 1));
                                if (coachObject.get("Coach_Name_" + coachIndex).toString().equals(cancelledCoach)) {
                                    JSONArray seatArray = (JSONArray) coachObject.get("Seats_" + coachIndex);

                                    for (int seatIndex = 1; seatIndex <= seatArray.size(); seatIndex++) {
                                        JSONObject seatObject = (JSONObject) seatArray.get((seatIndex - 1));
                                        JSONArray array1 = (JSONArray) seatObject.get("Seat_Status");

                                        for (int arrIndex = 0; arrIndex < array1.size(); arrIndex++) {
                                            JSONObject arrObj = (JSONObject) array1.get(arrIndex);
                                            if (arrObj.get("Date").toString().equals(cancelledDate)) {
                                                if (arrObj.get("Destination").toString().equals(cancelledDestination) && arrObj.get("Source").toString().equals(cancelledSource)) {
                                                    if (arrObj.get("Status").toString().equals("Waiting")) {
                                                        arrObj.replace("Status", "Confirmed");
                                                    }
                                                    if (arrObj.get("Status").toString().equals("Confirmed")) {
                                                        array1.remove(arrIndex);
                                                        cancelledNumOfTickets--;
                                                        break;
                                                    }

                                                }
                                            }
                                        }
                                        if (cancelledNumOfTickets == 0)
                                            break;
                                    }
                                }
                                if (cancelledNumOfTickets == 0)
                                    break;
                            }
                        }
                        if (cancelledNumOfTickets == 0)
                            break;
                    }
                    writeJson(jsonObject, filePath);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
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
            for (int i = 1; i <= seatArray.size(); i++) {
                JSONObject seatObject = (JSONObject) seatArray.get((i - 1));
                JSONArray array = (JSONArray) seatObject.get("Seat_Status");
                JSONObject obj1 = new JSONObject();
                if (array.size() >= 1) {
                    obj1.put("Status", "Waiting");
                } else {
                    obj1.put("Status", "Confirmed");
                }
                obj1.put("Date", BoardingDate);
                obj1.put("Source", getBoardingStation());
                obj1.put("Destination", getDestination());
                array.add(obj1);
                seatObject.put("Seat_Status", array);
                count++;
                if (count > numOfTickets)
                    break;
            }
            writeJson(jsonObject, filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String cancelledTrain;
    static String cancelledDate;
    static String cancelledSource;
    static String cancelledDestination;
    static String cancelledCoach;
    static String cancelledSeatNumber = null;
    static String cancelledSeatType = null;

    void cancelTicket(int ticketNumber) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            JSONObject ticketObject = (JSONObject) jsonParser.parse(fileReader);
            jsonObject = (JSONObject) ticketObject.get("Ticket_" + ticketNumber);
            cancelledTrain = jsonObject.get("Train_Name").toString();
            cancelledDate = jsonObject.get("Date").toString();
            cancelledSource = jsonObject.get("Boarding Station").toString();
            cancelledDestination = jsonObject.get("Destination").toString();

            JSONArray passengerArray = (JSONArray) jsonObject.get("Passengers");
            JSONObject passengerObject = (JSONObject) passengerArray.get(0);
            int cancelledNumOfTickets = (passengerObject.size() / 6);
            JSONObject coachObject = (JSONObject) passengerArray.get(0);

            cancelledCoach = coachObject.get("Passenger1_Coach_Name").toString();

            if (coachObject.get("Passenger1_Seat_Number") != null) {
                cancelledSeatNumber = coachObject.get("Passenger1_Seat_Number").toString();
                cancelledSeatType = coachObject.get("Passenger1_Seat_Type").toString();
            }
            ticketObject.remove("Ticket_" + ticketNumber);
            JSONObject finalObject = new JSONObject();
            int ticketIndex = 1;
            for (int i = 1; i < existingTicketsCount(); i++) {
                if (ticketIndex == ticketNumber)
                    ticketIndex++;
                finalObject.put("Ticket_" + i, ticketObject.get("Ticket_" + ticketIndex++));
            }
            writeJson(finalObject, ticketFilePath);
            updateOnCancel(cancelledNumOfTickets);

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
            FileReader fileReader = new FileReader(filePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);

            for (int trainIterator = 1; trainIterator <= existingTrainsCount(); trainIterator++) {
                JSONObject trains = (JSONObject) jsonObject.get("Train_" + (trainIterator));
                JSONObject operatingDays = (JSONObject) trains.get("Operating_Days");
                JSONObject route = (JSONObject) trains.get("Route");
                for (int dayIterator = 1; dayIterator < 7; dayIterator++) {
                    if (operatingDays.containsKey("Day_" + dayIterator)) {
                        if (operatingDays.get("Day_" + dayIterator).toString().equals(getTravelDay())) {
                            for (int routeIterator = 1; routeIterator <= route.size(); routeIterator++) {
                                if (route.get("Stop_" + routeIterator).toString().equals(getBoardingStation())) {
                                    for (int j = routeIterator + 1; j <= route.size(); j++) {
                                        if (route.get("Stop_" + j).toString().equals(getDestination())) {
                                            availableTrainArray.add(trains);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return availableTrainArray;
    }

    public JSONObject viewTicket(String bookingID) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(ticketFilePath);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            for (int i = 0; i < existingTicketsCount(); i++) {
                JSONObject tempObject = (JSONObject) jsonObject.get("Ticket_" + (i + 1));
                String tempObject1 = tempObject.get("Booking_ID").toString();
                if (bookingID.equals(tempObject1)) {
                    return tempObject;
                }
            }
        } catch (ClassCastException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
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
        } catch (ClassCastException | IOException | ParseException e) {
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
