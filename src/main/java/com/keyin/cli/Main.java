package com.keyin.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Airport CLI Frontend ===");
            System.out.println("1. What airports are in a city?");
            System.out.println("2. What aircraft has a passenger flown on?");
            System.out.println("3. What airports does an aircraft use?");
            System.out.println("4. What airports has a passenger used?");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> showAirportsByCity();
                    case 2 -> showAircraftByPassenger();
                    case 3 -> showAirportsByAircraft();
                    case 4 -> showAirportsByPassenger();
                    case 5 -> {
                        System.out.println("Exiting CLI...");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error while contacting backend: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // Q1: What airports are in a city?
    private static void showAirportsByCity() throws Exception {
        List<City> cities = ApiClient.getList("/cities", new TypeReference<>() {});

        for (City city : cities) {
            System.out.println("\n" + city.getName() + " ->");

            // ✅ Correct endpoint: /airports/city/{id}
            List<Airport> airports = ApiClient.getList(
                    "/airports/city/" + city.getId(),
                    new TypeReference<>() {}
            );

            if (airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport a : airports) {
                    System.out.println("   - " + a.getName() + " (" + a.getCode() + ")");
                }
            }
        }
    }

    // Q2: What aircraft has each passenger flown on?
    private static void showAircraftByPassenger() throws Exception {
        List<Passenger> passengers = ApiClient.getList("/passengers", new TypeReference<>() {});

        for (Passenger p : passengers) {
            System.out.println("\n" + p.getFirstName() + " " + p.getLastName() + " flew on:");

            // ✅ Correct endpoint: /aircraft/passenger/{id}
            List<Aircraft> aircraft = ApiClient.getList(
                    "/aircraft/passenger/" + p.getId(),
                    new TypeReference<>() {}
            );

            if (aircraft.isEmpty()) {
                System.out.println("   No aircraft found.");
            } else {
                for (Aircraft a : aircraft) {
                    System.out.println("   - " + a.getType() + " (" + a.getAirlineName() + ")");
                }
            }
        }
    }

    // Q3: What airports does an aircraft use?
    private static void showAirportsByAircraft() throws Exception {
        List<Aircraft> aircraftList = ApiClient.getList("/aircraft", new TypeReference<>() {});

        for (Aircraft a : aircraftList) {
            System.out.println("\n" + a.getType() + " (" + a.getAirlineName() + ") ->");

            // ✅ Endpoint is already correct
            List<Airport> airports = ApiClient.getList(
                    "/aircraft/" + a.getId() + "/airports",
                    new TypeReference<>() {}
            );

            if (airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport ap : airports) {
                    System.out.println("   - " + ap.getName() + " (" + ap.getCode() + ")");
                }
            }
        }
    }

    // Q4: What airports has a passenger used?
    private static void showAirportsByPassenger() throws Exception {
        List<Passenger> passengers = ApiClient.getList("/passengers", new TypeReference<>() {});

        for (Passenger p : passengers) {
            System.out.println("\n" + p.getFirstName() + " " + p.getLastName() + " used airports:");

            // ✅ Endpoint is already correct
            List<Airport> airports = ApiClient.getList(
                    "/passengers/" + p.getId() + "/airports",
                    new TypeReference<>() {}
            );

            if (airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport ap : airports) {
                    System.out.println("   - " + ap.getName() + " (" + ap.getCode() + ")");
                }
            }
        }
    }
}
