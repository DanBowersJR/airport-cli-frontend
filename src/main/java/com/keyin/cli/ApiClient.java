package com.keyin.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Generic: GET for a single object
    public static <T> T get(String endpoint, Class<T> responseType) throws Exception {
        String url = BASE_URL + endpoint;
        System.out.println("➡️ Calling: " + url);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            return client.execute(request, response -> {
                int status = response.getCode();
                if (status >= 200 && status < 300) {
                    String json = EntityUtils.toString(response.getEntity());
                    return mapper.readValue(json, responseType);
                } else {
                    throw new RuntimeException("API call failed: " + status + " (" + url + ")");
                }
            });
        }
    }

    // Generic: GET for a list
    public static <T> List<T> getList(String endpoint, TypeReference<List<T>> typeRef) throws Exception {
        String url = BASE_URL + endpoint;
        System.out.println("➡️ Calling: " + url);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            return client.execute(request, response -> {
                int status = response.getCode();
                if (status >= 200 && status < 300) {
                    String json = EntityUtils.toString(response.getEntity());
                    return mapper.readValue(json, typeRef);
                } else {
                    throw new RuntimeException("API call failed: " + status + " (" + url + ")");
                }
            });
        }
    }

    // -------------------------
    // 🚀 Convenience methods
    // -------------------------

    // Q1: What airports are there in each city?
    public static <T> List<T> getAirportsByCity(Long cityId, TypeReference<List<T>> typeRef) throws Exception {
        return getList("/airports/city/" + cityId, typeRef);
    }

    // Q2: What aircraft has each passenger flown on?
    public static <T> List<T> getAircraftByPassenger(Long passengerId, TypeReference<List<T>> typeRef) throws Exception {
        return getList("/aircraft/passenger/" + passengerId, typeRef);
    }

    // Q3: What airports do aircraft take off from and land at?
    public static <T> List<T> getAirportsForAircraft(Long aircraftId, TypeReference<List<T>> typeRef) throws Exception {
        return getList("/aircraft/" + aircraftId + "/airports", typeRef);
    }

    // Q4: What airports have passengers used?
    public static <T> List<T> getAirportsUsedByPassenger(Long passengerId, TypeReference<List<T>> typeRef) throws Exception {
        return getList("/passengers/" + passengerId + "/airports", typeRef);
    }
}
