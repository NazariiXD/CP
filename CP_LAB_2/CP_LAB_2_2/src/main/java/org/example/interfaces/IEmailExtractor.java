package org.example.interfaces;

import java.util.List;

public interface IEmailExtractor {
    List<String> getValidEmails(String text);
}

