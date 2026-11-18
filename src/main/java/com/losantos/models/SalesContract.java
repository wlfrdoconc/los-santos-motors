package com.losantos.models;

// Jofre - feature/sales, work on SalesContract

/** MISSION 2: Calculate tax, fees, and optional financed monthly payments. */
public class SalesContract extends Contract {
    private boolean finance;

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicle, boolean finance) {
        super(date, customerName, customerEmail, vehicle);
        this.finance = finance;
    }

    public boolean isFinance() { return finance; }
    public void setFinance(boolean finance) { this.finance = finance; }

    @Override
    public double getTotalPrice() {
        // TODO: vehicle price + 5% tax + $100 recording fee + processing fee.
        // Los Santos Motors applies the following fees:
        // Fee Rule
        // Sales tax 5% of vehicle price
        // Recording fee $100
        // Processing fee $295 for vehicles below $10,000
        // Processing fee $495 for vehicles of $10,000 or more
        double vehiclePrice = getVehicle().getPrice();
        double processingFee = vehiclePrice < 10000 ? 295 : 495;
        return vehiclePrice * 1.05 + 100 + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        // TODO: return 0 when not financed; otherwise amortize the full total.
        throw new UnsupportedOperationException("MISSION 2: Implement sales payment");
    }
}
