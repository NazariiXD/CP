package org.example.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LicensePlateDataHandlerTests {
    private LicensePlateDataHandler handler;
    private File tempFile;
    private List<String> licensePlates;

    @BeforeEach
    void setUp() throws IOException {
        handler = new LicensePlateDataHandler();
        tempFile = File.createTempFile("vehicles-license-plates", ".dat");

        licensePlates = List.of("ABC123", "XYZ456", "XYZ555");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists() && !tempFile.delete()) {
            System.err.println("Warning: Temporary file could not be deleted.");
        }
    }

    @Test
    void testWrite_shouldWriteLicensePlatesToFile() throws IOException {
        handler.write(licensePlates, tempFile.getPath());

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
    void testRead_shouldReadLicensePlatesFromFile() throws IOException {
        handler.write(licensePlates, tempFile.getPath());

        List<String> readLicensePlates = handler.read(tempFile.getPath());

        assertNotNull(readLicensePlates);
        assertEquals(licensePlates.size(), readLicensePlates.size());

        for (var i = 0; i < licensePlates.size(); i++) {
            assertEquals(licensePlates.get(i), readLicensePlates.get(i));
        }
    }

    @Test
    void testRead_withEmptyFileName_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.read(""));
    }
}
