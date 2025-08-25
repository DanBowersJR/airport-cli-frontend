package com.keyin.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Production API implementation (calls your existing ApiClient).
     * Tests will mock the BackendApi instead of hitting HTTP.
     */
    public interface BackendApi {
        List<City> getCities() throws Exception;
        List<Airport> getAirportsByCityId(long cityId) throws Exception;

        List<Passenger> getPassengers() throws Exception;
        List<Aircraft> getAircraftByPassenger(long passengerId) throws Exception;

        List<Aircraft> getAircraft() throws Exception;
        List<Airport> getAirportsByAircraft(long aircraftId) throws Exception;

        List<Airport> getAirportsByPassenger(long passengerId) throws Exception;
    }

    /** Real implementation used by the CLI at runtime */
    private static class RealApi implements BackendApi {
        @Override public List<City> getCities() throws Exception {
            return ApiClient.getList("/cities", new TypeReference<>() {});
        }
        @Override public List<Airport> getAirportsByCityId(long cityId) throws Exception {
            return ApiClient.getList("/airports/city/" + cityId, new TypeReference<>() {});
        }
        @Override public List<Passenger> getPassengers() throws Exception {
            return ApiClient.getList("/passengers", new TypeReference<>() {});
        }
        @Override public List<Aircraft> getAircraftByPassenger(long passengerId) throws Exception {
            return ApiClient.getList("/aircraft/passenger/" + passengerId, new TypeReference<>() {});
        }
        @Override public List<Aircraft> getAircraft() throws Exception {
            return ApiClient.getList("/aircraft", new TypeReference<>() {});
        }
        @Override public List<Airport> getAirportsByAircraft(long aircraftId) throws Exception {
            return ApiClient.getList("/aircraft/" + aircraftId + "/airports", new TypeReference<>() {});
        }
        @Override public List<Airport> getAirportsByPassenger(long passengerId) throws Exception {
            return ApiClient.getList("/passengers/" + passengerId + "/airports", new TypeReference<>() {});
        }
    }

    // The instance the CLI uses (tests will pass a mock instead)
    static final BackendApi api = new RealApi();

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
                    case 1 -> showAirportsByCity(api);       // uses injected api
                    case 2 -> showAircraftByPassenger(api);
                    case 3 -> showAirportsByAircraft(api);
                    case 4 -> showAirportsByPassenger(api);
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

    // ===== Testable helpers (each depends ONLY on BackendApi) =====

    // Q1: What airports are in a city?
    static void showAirportsByCity(BackendApi api) throws Exception {
        List<City> cities = api.getCities();
        for (City city : cities) {
            System.out.println("\n" + city.getName() + " ->");
            List<Airport> airports = api.getAirportsByCityId(city.getId());
            if (airports == null || airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport a : airports) {
                    System.out.println("   - " + a.getName() + " (" + a.getCode() + ")");
                }
            }
        }
    }

    // Q2: What aircraft has each passenger flown on?
    static void showAircraftByPassenger(BackendApi api) throws Exception {
        List<Passenger> passengers = api.getPassengers();
        for (Passenger p : passengers) {
            System.out.println("\n" + p.getFirstName() + " " + p.getLastName() + " flew on:");
            List<Aircraft> aircraft = api.getAircraftByPassenger(p.getId());
            if (aircraft == null || aircraft.isEmpty()) {
                System.out.println("   No aircraft found.");
            } else {
                for (Aircraft a : aircraft) {
                    System.out.println("   - " + a.getType() + " (" + a.getAirlineName() + ")");
                }
            }
        }
    }

    // Q3: What airports does an aircraft use?
    static void showAirportsByAircraft(BackendApi api) throws Exception {
        List<Aircraft> aircraftList = api.getAircraft();
        for (Aircraft a : aircraftList) {
            System.out.println("\n" + a.getType() + " (" + a.getAirlineName() + ") ->");
            List<Airport> airports = api.getAirportsByAircraft(a.getId());
            if (airports == null || airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport ap : airports) {
                    System.out.println("   - " + ap.getName() + " (" + ap.getCode() + ")");
                }
            }
        }
    }

    // Q4: What airports has a passenger used?
    static void showAirportsByPassenger(BackendApi api) throws Exception {
        List<Passenger> passengers = api.getPassengers();
        for (Passenger p : passengers) {
            System.out.println("\n" + p.getFirstName() + " " + p.getLastName() + " used airports:");
            List<Airport> airports = api.getAirportsByPassenger(p.getId());
            if (airports == null || airports.isEmpty()) {
                System.out.println("   No airports found.");
            } else {
                for (Airport ap : airports) {
                    System.out.println("   - " + ap.getName() + " (" + ap.getCode() + ")");
                }
            }
        }
    }
}
