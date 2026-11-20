package com.losantos.models;

// Sama - feature/leasing
// Merged Sama's changes into feature/transactions-ui branch

import java.time.LocalDate;

/** MISSION 3: Calculate the lease amount and its 36-month payment. */
public class LeaseContract extends Contract {

    // Constructor:
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);

        // Extra error validation to prevent incorrect lease contract objects
        if (vehicle == null) {
            throw new IllegalArgumentException("ERROR: A lease contract requires a vehicle.");
        }

        if (!isEligibleForLease(vehicle)) {
            throw new IllegalArgumentException("ERROR: Vehicle age is not eligible for leasing. Must be 3 years old or newer.");
        }
    }

    // Method: getTotalPrice()
    // Overridden from parent (Contract) (also in child - Sales)
    // Returns the total lease amount
    @Override
    public double getTotalPrice() {
        // TODO: price - 50% expected ending value + 7% lease fee.

        // Initialize and calculate values necessary for the total lease price formula
        double vehiclePrice = getVehicle().getPrice();
        double expectedEndingValue = 0.5 * vehiclePrice;
        double leaseFee = 0.07 * vehiclePrice;

        // Calculate total lease amount
        double totalLeasePrice = vehiclePrice - expectedEndingValue + leaseFee;

        return totalLeasePrice;
    }

    // Method: getMonthlyPayment()
    // Overridden from parent (Contract) (also in child - Sales)
    // Calculates the monthly payment using the same loan formula from Mission 2, with 4% annual interest and 36 months
    @Override
    public double getMonthlyPayment() {
        // TODO: amortize getTotalPrice() at 4% annual interest over 36 months.

        // Initialize and calculate values necessary for loan payment formula
        // Need M(monthly payment), P(total lease amount), r(monthly interest rate), n(# of monthly payments)
        double totalLeasePrice = getTotalPrice();
        double annualInterestRate = 0.04;
        double monthlyInterestRate = annualInterestRate / 12;
        int numberOfMonths = 36;

        // Calculate monthly payment amount
        double monthlyPayment = (totalLeasePrice * monthlyInterestRate) /
                (1 - Math.pow((1 + monthlyInterestRate), -numberOfMonths));
        return monthlyPayment;
        // round the monthly payment by formatting in UserInterface?
    }

    // Method: isEligibleForLease()
    // Checks if a vehicle is of eligible age before creating a LeaseContract object
    public static boolean isEligibleForLease(Vehicle vehicle) {

        int currentYear = LocalDate.now().getYear();
        int vehicleAge = currentYear - vehicle.getYear();
        boolean isEligible = false;

        if (vehicleAge >= 0 && vehicleAge <= 3) {
            isEligible = true;
        }

        return isEligible;
    }

}