package com.keyin.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api"; // backend base URL
    private static final ObjectMapper mapper = new ObjectMapper(); // for JSON <-> Java

    // Generic GET method that returns parsed Java objects
    public static <T> T get(String endpoint, Class<T> responseType) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + endpoint);
            return client.execute(request, response -> {
                String json = EntityUtils.toString(response.getEntity());
                return mapper.readValue(json, responseType);
            });
        }
    }
}
