package com.losantos.models;

public class Vehicle {
    private final String vin;
    private final int year;
    private final String make;
    private final String model;
    private final String vehicleType;
    private final String color;
    private final int odometer;
    private final double price;

    public Vehicle(String vin, int year, String make, String model, String vehicleType,
                   String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public String getVin() { return vin; }
    public int getYear() { return year; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getVehicleType() { return vehicleType; }
    public String getColor() { return color; }
    public int getOdometer() { return odometer; }
    public double getPrice() { return price; }

    public String toFileRecord() {
        return String.join("|", vin, String.valueOf(year), make, model,
                vehicleType, color, String.valueOf(odometer), String.valueOf(price));
    }

    @Override
    public String toString() {
        return String.format("%s | %s %s (%d) | %s | $%,.2f", vin, make, model, year, color, price);
    }
}
