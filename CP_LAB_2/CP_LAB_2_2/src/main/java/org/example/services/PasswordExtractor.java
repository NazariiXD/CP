package org.example.services;

import org.example.interfaces.IPasswordExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordExtractor implements IPasswordExtractor {
    private static final Pattern passwordPattern = Pattern.compile(
            "^(?=(.*[A-ZА-Я]))(?=(.*[a-zа-я]))(?=(.*\\d))(?=(.*[!@#$%^&*])).{8,20}$"
    );

    @Override
    public List<String> getValidPasswords(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be null");
        }

        text = text.replaceAll("[.,\"]", "");

        var validPasswords = new ArrayList<String>();

        var words = text.split("\\s+");

        for (var word : words) {
            if (passwordPattern.matcher(word).matches()) {
                validPasswords.add(word);
            }
        }

        return validPasswords;
    }
}
