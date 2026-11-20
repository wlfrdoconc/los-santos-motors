package com.losantos.ui;

import java.util.Scanner;

public class AdminUserInterface {

    // Ask for an admin password.
    // Allow access only if the password is correct.
    // Display a menu with options to view all contracts or the last 10 transactions.
    // Return to the main dealership menu when the admin exits.

    private Scanner scanner = new Scanner(System.in);
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
                    System.out.println("View all contracts");
                    // Need ContractFileManager
                    break;
                case "2":
                    System.out.println("View last 10 transactions");
                    // Display last 10 records
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
