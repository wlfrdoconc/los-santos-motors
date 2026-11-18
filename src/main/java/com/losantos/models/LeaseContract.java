package com.losantos.models;

// Sama - feature/leasing

/** MISSION 3: Calculate the lease amount and its 36-month payment. */
public class LeaseContract extends Contract {
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    // Method: getTotalPrice()
    // Overridden from parent (Contract) (also in child - Sales)
    // Returns the total lease amount
    @Override
    public double getTotalPrice() {
        // TODO: price - 50% expected ending value + 7% lease fee.

        // Calculate values necessary for the total lease price formula
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


        throw new UnsupportedOperationException("MISSION 3: Implement lease payment");
    }
}
