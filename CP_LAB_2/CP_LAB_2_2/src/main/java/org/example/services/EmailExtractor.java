package org.example.services;

import org.example.interfaces.IEmailExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmailExtractor implements IEmailExtractor {
    private static final String EMAIL_REGEX = "[\\p{IsCyrillic}A-Za-z0-9+._-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}";
    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public List<String> getValidEmails(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        var validEmails = new ArrayList<String>();
        var matcher = emailPattern.matcher(text);

        while (matcher.find()) {
            validEmails.add(matcher.group());
        }

        return validEmails;
    }
}
