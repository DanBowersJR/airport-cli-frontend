package com.keyin.cli;

import java.util.List;

public class Passenger {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long cityId;  // ✅ link back to city

    // ✅ keep IDs (for Q2 + Q4)
    private List<Long> aircraftIds;
    private List<Long> airportIds;

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Long getCityId() {
        return cityId;
    }
    public List<Long> getAircraftIds() {
        return aircraftIds;
    }
    public List<Long> getAirportIds() {
        return airportIds;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public void setAircraftIds(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }
    public void setAirportIds(List<Long> airportIds) {
        this.airportIds = airportIds;
    }
}
