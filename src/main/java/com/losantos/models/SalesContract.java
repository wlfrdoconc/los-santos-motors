package com.losantos.models;

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
        throw new UnsupportedOperationException("MISSION 2: Implement sales total");
    }

    @Override
    public double getMonthlyPayment() {
        // TODO: return 0 when not financed; otherwise amortize the full total.
        throw new UnsupportedOperationException("MISSION 2: Implement sales payment");
    }
}
