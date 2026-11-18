package com.losantos.models;

/** MISSION 3: Calculate the lease amount and its 36-month payment. */
public class LeaseContract extends Contract {
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    @Override
    public double getTotalPrice() {
        // TODO: price - 50% expected ending value + 7% lease fee.
        throw new UnsupportedOperationException("MISSION 3: Implement lease total");
    }

    @Override
    public double getMonthlyPayment() {
        // TODO: amortize getTotalPrice() at 4% annual interest over 36 months.
        throw new UnsupportedOperationException("MISSION 3: Implement lease payment");
    }
}
