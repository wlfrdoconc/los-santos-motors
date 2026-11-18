package com.losantos.ui;

import com.losantos.data.ContractFileManager;
import com.losantos.data.InventoryFileManager;
import com.losantos.models.Vehicle;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final InventoryFileManager inventoryFileManager;
    private final ContractFileManager contractFileManager;
    private List<Vehicle> inventory;

    public UserInterface(Path dataDirectory) throws IOException {
        inventoryFileManager = new InventoryFileManager(dataDirectory.resolve("inventory.csv"));
        contractFileManager = new ContractFileManager(dataDirectory.resolve("contracts.csv"));
        inventory = new ArrayList<>(inventoryFileManager.loadVehicles());
    }

    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== LOS SANTOS MOTORS ==========");
            System.out.println("1. Browse showroom");
            System.out.println("2. Search vehicles by VIN");
            System.out.println("3. Add vehicle");
            System.out.println("4. Remove vehicle");
            System.out.println("5. Sell / Lease vehicle [WORKSHOP MISSION]");
            System.out.println("0. Exit");
            String choice = ask("Choose: ");
            try {
                switch (choice) {
                    case "1" -> browse();
                    case "2" -> search();
                    case "3" -> addVehicle();
                    case "4" -> removeVehicle();
                    case "5" -> sellOrLeaseVehicle();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (IOException ex) {
                System.out.println("File error: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private void browse() {
        if (inventory.isEmpty()) { System.out.println("Showroom is empty."); return; }
        inventory.forEach(System.out::println);
    }

    private Vehicle findByVin(String vin) {
        return inventory.stream().filter(v -> v.getVin().equalsIgnoreCase(vin)).findFirst().orElse(null);
    }

    private void search() {
        Vehicle found = findByVin(ask("VIN: "));
        System.out.println(found == null ? "Vehicle not found." : found);
    }

    private void addVehicle() throws IOException {
        String vin = ask("VIN: ");
        if (vin.isBlank() || vin.contains("|")) throw new IllegalArgumentException("Invalid VIN.");
        if (findByVin(vin) != null) { System.out.println("VIN already exists."); return; }
        int year = Integer.parseInt(ask("Year: "));
        String make = safeField("Make: ");
        String model = safeField("Model: ");
        String type = safeField("Vehicle type: ");
        String color = safeField("Color: ");
        int odometer = Integer.parseInt(ask("Odometer: "));
        double price = Double.parseDouble(ask("Price: "));
        if (year < 1900 || year > java.time.Year.now().getValue() + 1 || odometer < 0 || !Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Invalid vehicle year, mileage, or price.");
        }
        List<Vehicle> updated = new ArrayList<>(inventory);
        updated.add(new Vehicle(vin, year, make, model, type, color, odometer, price));
        inventoryFileManager.saveVehicles(updated);
        inventory = updated;
        System.out.println("Vehicle added.");
    }

    private String safeField(String label) {
        String field = ask(label);
        if (field.isBlank() || field.contains("|")) throw new IllegalArgumentException("Invalid field value.");
        return field;
    }

    private void removeVehicle() throws IOException {
        Vehicle found = findByVin(ask("VIN: "));
        if (found == null) { System.out.println("Vehicle not found."); return; }
        List<Vehicle> updated = new ArrayList<>(inventory);
        updated.remove(found);
        inventoryFileManager.saveVehicles(updated);
        inventory = updated;
        System.out.println("Vehicle removed.");
    }

    private void sellOrLeaseVehicle() throws IOException {
        // MISSION 5: Implement the complete user interaction here.
        // 1. Ask for VIN and locate vehicle via findByVin().
        // 2. Ask for customer details and SALE/LEASE choice.
        // 3. Validate lease age using java.time.Year.now().getValue().
        // 4. For a sale, ask about financing.
        // 5. Create SalesContract OR LeaseContract (store in Contract variable).
        // 6. Print getTotalPrice() and getMonthlyPayment().
        // 7. Save with contractFileManager.saveContract(contract).
        // 8. Only after successful contract save, update vehicle inventory.
        // Think about what should happen if updating inventory fails.
        System.out.println("MISSION 5 is not implemented yet. See README.md.");
    }
}
