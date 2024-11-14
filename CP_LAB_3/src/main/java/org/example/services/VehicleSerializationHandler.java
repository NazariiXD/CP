package org.example.services;

import org.example.interfaces.VehicleDataHandler;
import org.example.models.Vehicle;

import java.io.*;
import java.util.List;

public class VehicleSerializationHandler implements VehicleDataHandler {
    @Override
    public void write(List<Vehicle> vehicles, String fileName) throws IllegalArgumentException, IOException  {
        if (vehicles == null) {
            throw new IllegalArgumentException("The list of vehicles cannot be null");
        }

        validateFilePath(fileName);

        try (var objectOut = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOut.writeObject(vehicles);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Vehicle> read(String fileName) throws IllegalArgumentException, IOException  {
        validateFilePath(fileName);

        try (var objectIn = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Vehicle>) objectIn.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Error reading vehicles data", e);
        }
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }
    }
}
