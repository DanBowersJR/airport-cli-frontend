package com.keyin.cli;

import java.util.List;

public class Aircraft {
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;

    // ✅ just keep IDs (for Q2 + Q3)
    private List<Long> airportIds;
    private List<Long> passengerIds;

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getAirlineName() {
        return airlineName;
    }
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    public List<Long> getAirportIds() {
        return airportIds;
    }
    public List<Long> getPassengerIds() {
        return passengerIds;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
    public void setAirportIds(List<Long> airportIds) {
        this.airportIds = airportIds;
    }
    public void setPassengerIds(List<Long> passengerIds) {
        this.passengerIds = passengerIds;
    }
}
