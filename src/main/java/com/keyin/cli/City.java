package com.keyin.cli;

import java.util.List;

public class City {
    private Long id;
    private String name;
    private String state;
    private int population;

    // ✅ store airport IDs only
    private List<Long> airportIds;

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getState() {
        return state;
    }
    public int getPopulation() {
        return population;
    }
    public List<Long> getAirportIds() {
        return airportIds;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public void setAirportIds(List<Long> airportIds) {
        this.airportIds = airportIds;
    }
}
