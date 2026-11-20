package com.losantos.models;

// Jofre - feature/sales, work on SalesContract
// Merged Jofre's changes into feature/transactions-ui branch

import java.util.ArrayList;

/** MISSION 2: Calculate tax, fees, and optional financed monthly payments. */
public class SalesContract extends Contract {
    private boolean finance;
    private ArrayList<AddOn> addOns = new ArrayList<>();

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicle, boolean finance) {
        super(date, customerName, customerEmail, vehicle);
        this.finance = finance;
    }

    public boolean isFinance() { return finance; }
    public void setFinance(boolean finance) { this.finance = finance; }

    @Override
    public double getTotalPrice() {
        // Los Santos Motors applies the following fees:
        double vehiclePrice = getVehicle().getPrice();

        // Sales tax 5% of vehicle price
        double salesTax = vehiclePrice * 0.05;

        // Recording fee $100
        double recordingFee = 100;

        // Processing fee $295 for vehicles below $10,000
        // Processing fee $495 for vehicles of $10,000 or more
        double processingFee = vehiclePrice < 10000 ? 295 : 495;

        // vehicle price + 5% tax + $100 recording fee + processing fee + upgrades
        return vehiclePrice + salesTax + recordingFee + processingFee + getAddOnTotal();
    }

    @Override
    public double getMonthlyPayment() {
        // return 0 when not financed; otherwise amortize the full total.
        if (!isFinance()) return 0;

        // Vehicle price | Interest rate | Duration
        // Below $10,000 | 5.25% annually | 24 months
        // $10,000 or more | 4.25% annually | 48 months
        double vehiclePrice = getVehicle().getPrice();
        double annualInterestRate = vehiclePrice < 10000 ? 0.0525 : 0.0425;

        // P = total amount financed
        double principal = getTotalPrice();

        // r = annual interest rate divided by 12
        double monthlyRate = annualInterestRate / 12;

        // n = number of monthly payments
        int months = vehiclePrice < 10000 ? 24 : 48;

        // M = P × r / (1 - (1 + r)^(-n))
        return principal * monthlyRate / (1 - Math.pow(1 + monthlyRate, -months));
    }

    // append AddOn to arraylist
    public void addAddOn(AddOn addOn) {
        addOns.add(addOn);
    }

    // getter for the AddOn arraylist
    public ArrayList<AddOn> getAddOns() {return addOns;}

    public double getAddOnTotal() {
        double total = 0;
        for (AddOn addOn : addOns) {
            total += addOn.getPrice();
        }
        return total;
    }
}
