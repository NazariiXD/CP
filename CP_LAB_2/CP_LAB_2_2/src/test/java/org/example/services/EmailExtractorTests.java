package org.example.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailExtractorTests {
    private final EmailExtractor emailExtractor = new EmailExtractor();

    @Test
    public void testEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailExtractor.getValidEmails("");
        });
    }

    @Test
    public void testNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailExtractor.getValidEmails(null);
        });
    }

    @Test
    public void testValidEmail() {
        var text = "Contact us at support@events.org and visit us at info@example.com.";

        var validEmails = emailExtractor.getValidEmails(text);

        assertEquals(2, validEmails.size());
        assertTrue(validEmails.contains("support@events.org"));
        assertTrue(validEmails.contains("info@example.com"));
    }

    @Test
    public void testInvalidEmail() {
        var text = "Invalid emails: @missingusername.com, invalid-email@, email@domain, test@domain..com";

        var validEmails = emailExtractor.getValidEmails(text);

        assertEquals(0, validEmails.size());
    }
}
