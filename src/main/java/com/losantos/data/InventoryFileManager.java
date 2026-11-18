package com.losantos.data;

import com.losantos.models.Vehicle;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InventoryFileManager {
    private static final String HEADER = "VIN|YEAR|MAKE|MODEL|VEHICLE_TYPE|COLOR|ODOMETER|PRICE";
    private final Path file;

    public InventoryFileManager(Path file) { this.file = file; }

    public List<Vehicle> loadVehicles() throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        if (!Files.exists(file)) throw new IOException("Inventory file not found: " + file.toAbsolutePath());
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.equalsIgnoreCase(HEADER)) continue;
            String[] fields = line.split("\\|", -1);
            if (fields.length != 8) throw new IOException("Invalid inventory row at line " + (i + 1));
            try {
                vehicles.add(new Vehicle(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3],
                        fields[4], fields[5], Integer.parseInt(fields[6]), Double.parseDouble(fields[7])));
            } catch (NumberFormatException ex) {
                throw new IOException("Invalid number on inventory line " + (i + 1), ex);
            }
        }
        return vehicles;
    }

    public void saveVehicles(List<Vehicle> vehicles) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Vehicle vehicle : vehicles) lines.add(vehicle.toFileRecord());
        Files.write(file, lines, StandardCharsets.UTF_8);
    }
}
