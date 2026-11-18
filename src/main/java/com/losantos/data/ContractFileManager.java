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

// Thuan - feature/contracts-file, work on ContractFileManager class

/** MISSION 4: Append sale and lease contracts to a single file. */
public class ContractFileManager {
    private final Path file;

    public ContractFileManager(Path file) { this.file = file; }

    public void saveContract(Contract contract) throws IOException {
        // TODO: use instanceof to distinguish SalesContract from LeaseContract.
        // TODO: format ALL required common and contract-specific fields separated by '|'.
        // TODO: append one record using Files.writeString(..., CREATE, APPEND).
        // IMPORTANT: do NOT overwrite older records.
        Vehicle v = contract.getVehicle(); // Gets Vehicle connected to contract
        // Holds record
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

            // Building Sales record

        }
        throw new UnsupportedOperationException("Error, Please try again");
    }
}
