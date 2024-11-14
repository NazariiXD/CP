package org.example.services;

import org.example.models.Brand;
import org.example.models.Vehicle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleSerializationJsonHandlerTests {
    private VehicleSerializationJsonHandler handler;
    private File tempFile;
    private List<Vehicle> vehicles;

    @BeforeEach
    void setUp() throws IOException {
        handler = new VehicleSerializationJsonHandler();
        tempFile = File.createTempFile("vehicles", ".json");

        vehicles = List.of(
                new Vehicle("ABC123", new Brand("Toyota", "Japan"), 2020),
                new Vehicle("XYZ456", new Brand("Ford", "USA"), 2018)
        );
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists() && !tempFile.delete()) {
            System.err.println("Warning: Temporary file could not be deleted.");
        }
    }

    @Test
    void testWrite_shouldWriteVehiclesToJsonFile() throws IOException {
        handler.write(vehicles, tempFile.getPath());

        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void testWrite_withNullList_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.write(null, tempFile.getPath()));
    }

    @Test
    void testWrite_withEmptyFileName_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.write(new ArrayList<>(), ""));
    }

    @Test
    void testWrite_withInvalidFileExtension_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.write(new ArrayList<>(), "invalid.txt"));
    }

    @Test
    void testRead_shouldReadVehiclesFromJsonFile() throws IOException {
        handler.write(vehicles, tempFile.getPath());

        List<Vehicle> readVehicles = handler.read(tempFile.getPath());

        assertNotNull(readVehicles);
        assertEquals(vehicles.size(), readVehicles.size());

        for (var i = 0; i < vehicles.size(); i++) {
            assertEquals(vehicles.get(i).getLicensePlate(), readVehicles.get(i).getLicensePlate());
            assertEquals(vehicles.get(i).getBrand().getName(), readVehicles.get(i).getBrand().getName());
            assertEquals(0, readVehicles.get(i).getYear());
        }
    }

    @Test
    void testRead_withEmptyFileName_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.read(""));
    }

    @Test
    void testRead_withInvalidFileExtension_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.read("invalid.txt"));
    }
}
