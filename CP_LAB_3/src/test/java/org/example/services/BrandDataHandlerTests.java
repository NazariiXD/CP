package org.example.services;

import org.example.models.Brand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrandDataHandlerTests {
    private BrandDataHandler handler;
    private File tempFile;
    private List<Brand> brands;

    @BeforeEach
    void setUp() throws IOException {
        handler = new BrandDataHandler();
        tempFile = File.createTempFile("vehicles-brands", ".dat");

        brands = List.of(
                new Brand("Toyota", "Japan"),
                new Brand("Ford", "USA"),
                new Brand("Ford", "USA")
        );
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists() && !tempFile.delete()) {
            System.err.println("Warning: Temporary file could not be deleted.");
        }
    }

    @Test
    void testWrite_shouldWriteBrandsToFile() throws IOException {
        handler.write(brands, tempFile.getPath());

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
    void testRead_shouldReadBrandsFromFile() throws IOException {
        handler.write(brands, tempFile.getPath());

        List<Brand> readBrands = handler.read(tempFile.getPath());

        assertNotNull(readBrands);
        assertEquals(brands.size(), readBrands.size());

        for (var i = 0; i < brands.size(); i++) {
            assertEquals(brands.get(i).getName(), readBrands.get(i).getName());
            assertEquals(brands.get(i).getCountry(), readBrands.get(i).getCountry());
        }
    }

    @Test
    void testRead_withEmptyFileName_shouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.read(""));
    }
}
