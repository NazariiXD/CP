package org.example.services;

import org.example.interfaces.DataHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class YearDataHandler implements DataHandler<Integer> {
    @Override
    public void write(List<Integer> years, String fileName) throws IllegalArgumentException, IOException {
        if (years == null) {
            throw new IllegalArgumentException("The list of years cannot be null");
        }

        validateFilePath(fileName);

        try (var dataOut = new DataOutputStream(new FileOutputStream(fileName))) {
            dataOut.writeInt(years.size());

            for (var year : years) {
                dataOut.writeInt(year);
            }
        }
    }

    @Override
    public List<Integer> read(String fileName) throws IllegalArgumentException, IOException {
        validateFilePath(fileName);

        var years = new ArrayList<Integer>();

        try (var dataIn = new DataInputStream(new FileInputStream(fileName))) {
            int size = dataIn.readInt();

            for (var i = 0; i < size; i++) {
                var year = dataIn.readInt();
                years.add(year);
            }
        }

        return years;
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }
    }
}
