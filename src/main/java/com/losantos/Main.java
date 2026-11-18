package com.losantos;

import com.losantos.ui.UserInterface;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path dataDir = args.length > 0 ? Path.of(args[0]) : Path.of("data");
        try {
            new UserInterface(dataDir).display();
        } catch (IOException ex) {
            System.err.println("Could not start dealership: " + ex.getMessage());
        }
    }
}
