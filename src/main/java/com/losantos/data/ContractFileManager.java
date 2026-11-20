package com.losantos.data;
// Import the parent Contract class.
import com.losantos.models.Contract;
// Import the two types of contracts.
import com.losantos.models.LeaseContract;
import com.losantos.models.SalesContract;
// Import Vehicle so we can access the vehicle attached to a contract.
import com.losantos.models.Vehicle;
// Import tools needed to write to a file.
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.ArrayList;
import java.io.BufferedReader;
import com.losantos.models.AddOn;

// Thuan - feature/contracts-file, work on ContractFileManager class

/** MISSION 4: Append sale and lease contracts to a single file. */
public class ContractFileManager {
    private final Path file;

    public ContractFileManager(Path file) {
        this.file = file;
    }

    public void saveContract(Contract contract) throws IOException {
        // TODO: use instanceof to distinguish SalesContract from LeaseContract.
        // TODO: format ALL required common and contract-specific fields separated by '|'.
        // TODO: append one record using Files.writeString(..., CREATE, APPEND).
        // IMPORTANT: do NOT overwrite older records.
        Vehicle v = contract.getVehicle(); // Gets Vehicle connected to contract, stores into v
        // String variable to store contract info
        String record;
        // Checks if contract is sales contract
        if (contract instanceof SalesContract sale) {
            // Gets vehicle's original price
            double vehiclePrice = v.getPrice();
            // Calculates sales tax
            double salesTax = vehiclePrice * 0.05; // 5% sales tax
            // Recording fee = $100.00
            double recordingFee = 100.00;

            // ? vehicle price < $10,000, charge 295.00 : charge 495.00
            double processingFee = (vehiclePrice < 10000) ? 295.00 : 495.00;

            // Save upgrades in a single CSV field
            String addOnRecord = "";

            for (AddOn addOn : sale.getAddOns()) {
                if (!addOnRecord.isEmpty()) {
                    addOnRecord += ";";
                }
                addOnRecord += addOn.getName() + ":" + addOn.getPrice();
            }

            // Building Sales record
            record = ("Sale" + "|" +
                    contract.getDate() + "|" +
                    contract.getCustomerName() + "|" +
                    contract.getCustomerEmail() + "|" +
                    // Saves all fields from vehicle.java
                    v.toFileRecord() + "|" +
                    String.format(Locale.US, "%.2f", salesTax) + "|" +// Saves sales tax with 2 decimals
                    String.format(Locale.US, "%.2f", recordingFee) + "|" +// Saves recording fee, 2 decimals
                    String.format(Locale.US, "%.2f", processingFee) + "|" +// Saves processing fee
                    // Saves total price from sales contract
                    String.format(Locale.US, "%.2f", sale.getTotalPrice()) + "|" +
                    //Saves whether customer chooses financing or not
                    (sale.isFinance() ? "YES" : "NO") + "|" +
                    // Saves monthly payment
                    String.format(Locale.US, "%.2f", sale.getMonthlyPayment()) + "|" +
                    addOnRecord);
        }
        // Checking for Lease contract
        else if (contract instanceof LeaseContract lease) {
            // Gets vehicle original price
            double vehiclePrice = v.getPrice();
            // Calculating the expected ending value (50% of original price)
            double endingValue = vehiclePrice * 0.50;
            // Calculating lease fee (7% of price)
            double leaseFee = vehiclePrice * 0.07;
            // Recording lease
            record = "Lease" + "|" +
                    contract.getDate() + "|" +
                    contract.getCustomerName() + "|" +
                    contract.getCustomerEmail() + "|" +
                    v.toFileRecord() + "|" + // Saves all fields from Vehicle.java
                    // Saves lease fee
                    String.format(Locale.US, "%.2f", endingValue) + "|" +
                    String.format(Locale.US, "%.2f", leaseFee) + "|" +
                    String.format(Locale.US, "%.2f", lease.getTotalPrice()) + "|" +
                    // Saves monthly lease payment
                    String.format(Locale.US, "%.2f", lease.getMonthlyPayment());
        }
        else {
            throw new UnsupportedOperationException("Error, Please try again");
        }
        // Getting parent folder contracts.csv
        Path parent = file.getParent();
        // Checks if parent folder exists, if yes, then leaves unchanged
        if (parent != null) {
            Files.createDirectories(parent);
        }
        // Writes completed record to contracts.csv
        Files.writeString(file,
                record + System.lineSeparator(), // Adds line break so next contract gets its own line
                StandardOpenOption.CREATE, // Creates contract.csv if it doesn't exist
                StandardOpenOption.APPEND); // Adds new record without deleting older ones
    }

    public ArrayList<Contract> loadContracts() throws IOException {

        ArrayList<Contract> contracts = new ArrayList<>();

        if (!Files.exists(file)) {
            return contracts;
        }

        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.isEmpty()) {

                    String[] fields = line.split("\\|", -1);

                    String type = fields[0];
                    String date = fields[1];
                    String customerName = fields[2];
                    String customerEmail = fields[3];

                    // Recreate the vehicle saved in the contract
                    Vehicle vehicle = new Vehicle(
                            fields[4],
                            Integer.parseInt(fields[5]),
                            fields[6],
                            fields[7],
                            fields[8],
                            fields[9],
                            Integer.parseInt(fields[10]),
                            Double.parseDouble(fields[11])
                    );

                    if (type.equalsIgnoreCase("Sale")) {

                        boolean finance = fields[16].equalsIgnoreCase("YES");

                        SalesContract sale = new SalesContract(
                                date, customerName, customerEmail,
                                vehicle, finance
                        );

                        // Restore saved add-ons
                        if (fields.length > 18 && !fields[18].isEmpty()) {

                            String[] addOnRecords = fields[18].split(";");

                            for (String addOnRecord : addOnRecords) {

                                String[] addOnFields = addOnRecord.split(":");

                                String addOnName = addOnFields[0];
                                double addOnPrice = Double.parseDouble(addOnFields[1]);

                                // Use AddOn class existing method
                                sale.addAddOn(new AddOn(addOnName, addOnPrice));
                            }
                        }

                        contracts.add(sale);

                    } else if (type.equalsIgnoreCase("Lease")) {

                        LeaseContract lease = new LeaseContract(
                                date, customerName, customerEmail,
                                vehicle
                        );

                        contracts.add(lease);
                    }
                }
            }
        }
        return contracts;
    }

}