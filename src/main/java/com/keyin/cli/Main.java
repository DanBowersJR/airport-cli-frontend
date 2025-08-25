package com.keyin.cli;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Airport CLI Frontend ===");
            System.out.println("1. What airports are in a city?");
            System.out.println("2. What aircraft has a passenger flown on?");
            System.out.println("3. What airports does an aircraft use?");
            System.out.println("4. What airports has a passenger used?");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
        }

        scanner.close();
    }

    private static void showAirportsByCity() throws Exception {
        City[] cities = ApiClient.get("/cities", City[].class);
        for (City city : cities) {
            Airport[] airports = ApiClient.get("/cities/" + city.getId() + "/airports", Airport[].class);
            System.out.println(city.getName() + " ->");
            for (Airport a : airports) {
                System.out.println("  - " + a.getName() + " (" + a.getCode() + ")");
            }
        }
    }

    private static void showAircraftByPassenger() throws Exception {
        Passenger[] passengers = ApiClient.get("/passengers", Passenger[].class);
        for (Passenger p : passengers) {
            Aircraft[] aircraft = ApiClient.get("/passengers/" + p.getId() + "/aircraft", Aircraft[].class);
            System.out.println(p.getFirstName() + " " + p.getLastName() + " flew on:");
            for (Aircraft a : aircraft) {
                System.out.println("  - " + a.getType() + " (" + a.getAirlineName() + ")");
            }
        }
    }

    private static void showAirportsByAircraft() throws Exception {
        Aircraft[] aircraft = ApiClient.get("/aircraft", Aircraft[].class);
        for (Aircraft a : aircraft) {
            Airport[] airports = ApiClient.get("/aircraft/" + a.getId() + "/airports", Airport[].class);
            System.out.println(a.getType() + " (" + a.getAirlineName() + ") ->");
            for (Airport ap : airports) {
                System.out.println("  - " + ap.getName() + " (" + ap.getCode() + ")");
            }
        }
    }

    private static void showAirportsByPassenger() throws Exception {
        Passenger[] passengers = ApiClient.get("/passengers", Passenger[].class);
        for (Passenger p : passengers) {
            Airport[] airports = ApiClient.get("/passengers/" + p.getId() + "/airports", Airport[].class);
            System.out.println(p.getFirstName() + " " + p.getLastName() + " used airports:");
            for (Airport ap : airports) {
                System.out.println("  - " + ap.getName() + " (" + ap.getCode() + ")");
            }
        }
    }
}
