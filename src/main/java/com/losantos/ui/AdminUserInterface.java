package com.losantos.ui;

import com.losantos.data.ContractFileManager;
import com.losantos.models.Contract;
import com.losantos.models.SalesContract;
import com.losantos.models.LeaseContract;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminUserInterface {

    // Ask for an admin password.
    // Allow access only if the password is correct.
    // Display a menu with options to view all contracts or the last 10 transactions.
    // Return to the main dealership menu when the admin exits.

    private Scanner scanner;
    private ContractFileManager contractFileManager;

    // Constructor
    public AdminUserInterface(Scanner scanner, ContractFileManager contractFileManager) {
        this.scanner = scanner;
        this.contractFileManager = contractFileManager;
    }

    private static final String PASSWORD = "DragonBallZ";

    // Check for that the entered password is correct
    private boolean validatePassword() {
        System.out.println("Enter your password: ");
        String inputPassword = scanner.nextLine();

        return PASSWORD.equals(inputPassword);
    }

    public void displayMenu() {

        if (!validatePassword()) {
            System.out.println("Incorrect password. Access DENIED.");
            // Exit the admin menu
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== ADMIN TERMINAL ===");
            System.out.println("1. View All Contracts");
            System.out.println("2. View Last 10 Transactions");
            System.out.println("0. Return to Main Menu");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllContracts();
                    break;
                case "2":
                    viewLastTenTransactions();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllContracts() {

        try {
            ArrayList<Contract> contracts = contractFileManager.loadContracts();

            if (contracts.isEmpty()) {
                System.out.println("No contracts found.");
                return;
            }

            System.out.println("\n=== ALL CONTRACTS ===");

            for (Contract contract : contracts) {
                displayContract(contract);
            }

        } catch (IOException ex) {
            System.out.println("Error loading contracts: " + ex.getMessage());
        }
    }

    private void viewLastTenTransactions() {

        try {
            ArrayList<Contract> contracts = contractFileManager.loadContracts();

            if (contracts.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            System.out.println("\n=== LAST 10 TRANSACTIONS ===");

            int startIndex = Math.max(0, contracts.size() - 10);

            for (int i = startIndex; i < contracts.size(); i++) {
                displayContract(contracts.get(i));
            }

        } catch (IOException ex) {
            System.out.println("Error loading transactions: " + ex.getMessage());
        }
    }

    private void displayContract(Contract contract) {

        System.out.println("\n------------------------------");

        if (contract instanceof SalesContract) {
            System.out.println("Contract Type: SALE");
        } else if (contract instanceof LeaseContract) {
            System.out.println("Contract Type: LEASE");
        }

        System.out.println("Date: " + contract.getDate());
        System.out.println("Customer: " + contract.getCustomerName());
        System.out.println("Email: " + contract.getCustomerEmail());

        System.out.println("Vehicle: " +
                contract.getVehicle().getMake() + " " +
                contract.getVehicle().getModel());

        System.out.printf("Total Price: $%,.2f%n",
                contract.getTotalPrice());

        System.out.printf("Monthly Payment: $%,.2f%n",
                contract.getMonthlyPayment());

        System.out.println("------------------------------");
    }
}
