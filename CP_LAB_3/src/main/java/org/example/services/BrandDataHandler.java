package org.example.services;

import org.example.interfaces.DataHandler;
import org.example.models.Brand;

import java.io.*;
import java.util.List;

public class BrandDataHandler implements DataHandler<Brand> {
    @Override
    public void write(List<Brand> brands, String fileName) throws IllegalArgumentException, IOException {
        if (brands == null) {
            throw new IllegalArgumentException("The list of brands cannot be null");
        }

        validateFilePath(fileName);

        try (var objectOut = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOut.writeObject(brands);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Brand> read(String fileName) throws IllegalArgumentException, IOException {
        validateFilePath(fileName);

        try (var objectIn = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Brand>) objectIn.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Error reading brands data", e);
        }
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }
    }
}
