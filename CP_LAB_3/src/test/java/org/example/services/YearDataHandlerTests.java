package org.example.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class YearDataHandlerTests {
    private YearDataHandler handler;
    private File tempFile;
    private List<Integer> years;

    @BeforeEach
    void setUp() throws IOException {
        handler = new YearDataHandler();
        tempFile = File.createTempFile("vehicles-years", ".dat");

        years = List.of(2019, 2020, 2015);
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists() && !tempFile.delete()) {
            System.err.println("Warning: Temporary file could not be deleted.");
        }
    }

    @Test
    void testWrite_shouldWriteYearsToFile() throws IOException {
        handler.write(years, tempFile.getPath());

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
                () -> handler.write(years, ""));
    }

    @Test
    void testRead_shouldReadYearsFromFile() throws IOException {
        handler.write(years, tempFile.getPath());

        List<Integer> readYears = handler.read(tempFile.getPath());

        assertNotNull(readYears);
        assertEquals(years.size(), readYears.size());

        for (var i = 0; i < years.size(); i++) {
            assertEquals(years.get(i), readYears.get(i));
        }
    }

    @Test
    void testRead_withEmptyFileName_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.read(""));
    }
}
