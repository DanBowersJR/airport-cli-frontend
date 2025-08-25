package com.keyin.cli;

public class Aircraft {
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;

    // Getters
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

    // Setters
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
}
