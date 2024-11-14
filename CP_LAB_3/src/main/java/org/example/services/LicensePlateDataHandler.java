package org.example.services;

import org.example.interfaces.DataHandler;

import java.io.*;
import java.util.List;

public class LicensePlateDataHandler implements DataHandler<String> {
    @Override
    public void write(List<String> licensePlates, String fileName) throws IllegalArgumentException, IOException {
        if (licensePlates == null) {
            throw new IllegalArgumentException("The list of license plates cannot be null");
        }

        validateFilePath(fileName);

        try (var bufferedOut = new BufferedOutputStream(new FileOutputStream(fileName));
             var objectOut = new ObjectOutputStream(bufferedOut)) {
            objectOut.writeObject(licensePlates);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> read(String fileName) throws IllegalArgumentException, IOException {
        validateFilePath(fileName);

        try (var bufferedIn = new BufferedInputStream(new FileInputStream(fileName));
             var objectIn = new ObjectInputStream(bufferedIn)) {

            return (List<String>) objectIn.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Error reading license plates data", e);
        }
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }
    }
}
