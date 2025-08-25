package com.keyin.cli;

public class Airport {
    private Long id;
    private String name;
    private String code;
    private Long cityId;  // ✅ link back to city

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }
    public Long getCityId() {
        return cityId;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
