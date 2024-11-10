package org.example.interfaces;

import java.util.List;

public interface IPasswordExtractor {
    List<String> getValidPasswords(String text);
}
