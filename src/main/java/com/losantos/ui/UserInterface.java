package com.losantos.ui;

import com.losantos.data.ContractFileManager;
import com.losantos.data.InventoryFileManager;
import com.losantos.models.Contract;
import com.losantos.models.LeaseContract;
import com.losantos.models.SalesContract;
import com.losantos.models.Vehicle;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Will - feature/transactions-ui, work on UserInterface

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

    // FROM SAMA: LEASE VALIDATION METHOD [before creating a lease] ---->
    // if (!LeaseContract.isEligibleForLease(vehicle)) {
    //    System.out.println("Vehicle is too old for leasing.");
    //}

    // FROM SAMA: Formatting for getTotalPrice() and getMonthlyPayment() ---->
    // System.out.println("Total Payment: $%.2f%n", ???.getTotalPrice());
    // System.out.println("Monthly Payment: $%.2f%n", ???.getMonthlyPayment());

    // Will - UserInterface UI
    private void sellOrLeaseVehicle() throws IOException {
        // MISSION 5: Implement the complete user interaction here.

        // 1. Ask for VIN and locate vehicle via findByVin().
        String vin = ask("Enter Vehicle VIN: ");

        Vehicle vehicle = findByVin(vin);

        if (vehicle == null) {
            System.out.println("Vehicle is not found.");
            return;
        }

        System.out.println("Entered vehicle is found!");
        System.out.println(vehicle);

        // 2. Ask for customer details and SALE/LEASE choice.
        String customerName = safeField("Customer name: ");
        String customerEmail = safeField("Customer email: ");

        System.out.println("Choose transaction: "); // Refactor later
        System.out.println("1. Purchase");
        System.out.println("2. Lease");

        // Stores the customer choice
        String transactionChoice = ask("Enter choice: ");

        // Finance boolean to reference in #5
        boolean finance = false;

        if (transactionChoice.equals("1")) {
            // Purchase

            // 4. For a sale, ask about financing.
            String financingChoice = ask("Would you like to finance your purchase? (Y/N): ");

            if (financingChoice.equalsIgnoreCase("Y")) {
                finance = true;
            } else if (financingChoice.equalsIgnoreCase("N")) {
                finance = false;
            } else {
                System.out.println("Invalid financing choice.");
                return;
            }

        } else if (transactionChoice.equals("2")) {
            // 3. Lease - Validate eligibility using Sama's method
            if (!LeaseContract.isEligibleForLease(vehicle)) {
                System.out.println("Vehicle is not eligible for leasing.");
                return;
            }

            System.out.println("Vehicle is eligible for leasing!");
        } else {
            System.out.println("Invalid choice, please try again. Returning to the main menu");
            return;
        }

        // 5. Create SalesContract OR LeaseContract (store in Contract variable).
        Contract contract;

        String date = java.time.LocalDate.now().toString();

        if (transactionChoice.equals("1")) {
            // Purchase - Create Jofre's SalesContract
            contract = new SalesContract(date, customerName, customerEmail, vehicle, finance);
        } else {
            // Lease - Create Sama's LeaseContract
            contract = new LeaseContract(date, customerName, customerEmail, vehicle);
        }

        // 6. Print getTotalPrice() and getMonthlyPayment().
        System.out.printf("Total Price: $%.2f%n", contract.getTotalPrice());
        System.out.printf("Monthly Payment: $%.2f%n", contract.getMonthlyPayment());

        // 7. Save with contractFileManager.saveContract(contract).
        contractFileManager.saveContract(contract);

        System.out.println("Contract saved successfully!");

        // 8. Only after successful contract save, update vehicle inventory.
        List<Vehicle> updatedInventory = new ArrayList<>(inventory);

        updatedInventory.remove(vehicle);

        try {
            inventoryFileManager.saveVehicles(updatedInventory);
            inventory = updatedInventory;
        } catch (IOException e) {
            System.out.println("Contract saved, but inventory update failed.");
            System.out.println("Manual reconciliation required. Do not retry this transaction.");
            throw e;
        }

        System.out.println("Transaction completed successfully!");
    }
}
