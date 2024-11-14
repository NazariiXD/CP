package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.VehicleDataHandler;
import org.example.models.Vehicle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class VehicleSerializationJsonHandler implements VehicleDataHandler {
    private static final Type VEHICLE_LIST_TYPE = new TypeToken<List<Vehicle>>() {}.getType();

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @Override
    public void write(List<Vehicle> vehicles, String fileName) throws IllegalArgumentException, IOException {
        if (vehicles == null) {
            throw new IllegalArgumentException("The list of vehicles cannot be null");
        }

        validateFilePath(fileName);

        try (var writer = new FileWriter(fileName)) {
            gson.toJson(vehicles, writer);
        }
    }

    @Override
    public List<Vehicle> read(String fileName) throws IllegalArgumentException, IOException  {
        validateFilePath(fileName);

        try (var reader = new FileReader(fileName)) {
            return gson.fromJson(reader, VEHICLE_LIST_TYPE);
        }
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }

        if (!fileName.endsWith(".json")) {
            throw new IllegalArgumentException("The file must have a .json extension");
        }
    }
}
