package org.example.interfaces;

import java.io.IOException;
import java.util.List;

public interface DataHandler<T> {
    void write(List<T> objects, String fileName) throws IllegalArgumentException, IOException;
    List<T> read(String fileName) throws IllegalArgumentException, IOException;
}
