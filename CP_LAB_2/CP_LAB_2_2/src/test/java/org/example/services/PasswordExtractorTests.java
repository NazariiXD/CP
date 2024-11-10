package org.example.services;

import org.example.interfaces.IPasswordExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordExtractorTests {
    private final IPasswordExtractor passwordExtractor = new PasswordExtractor();

    @Test
    void testGetValidPasswords_withValidPasswordsInText() {
        var text = "Password123! ValidPass!1 somepashereis123 simplepass Test123#";

        var validPasswords = passwordExtractor.getValidPasswords(text);

        var expectedPasswords = List.of("Password123!", "ValidPass!1", "Test123#");

        assertEquals(expectedPasswords.size(), validPasswords.size());
        assertTrue(validPasswords.containsAll(expectedPasswords));
    }

    @Test
    void testGetValidPasswords_withNoValidPasswordsInText() {
        var text = "Цей текст не містить жодного password noDigit! Wrong#Case можливого пароля. Всі слова звичайні, без спеціальних символів.";
        var validPasswords = passwordExtractor.getValidPasswords(text);

        assertTrue(validPasswords.isEmpty());
    }

    @Test
    void testGetValidPasswords_withMixedContent() {
        var text = "Можливі паролі: Val1dPass@, невірний пароль: qwerty, та ще один: Correct2022!.";
        var validPasswords = passwordExtractor.getValidPasswords(text);

        var expectedPasswords = List.of("Val1dPass@", "Correct2022!");

        assertEquals(expectedPasswords.size(), validPasswords.size());
        assertTrue(validPasswords.containsAll(expectedPasswords));
    }

    @Test
    void testGetValidPasswords_withEmptyText_ReturnsEmptyList() {
        var text = "";

        List<String> validPasswords = passwordExtractor.getValidPasswords(text);

        assertTrue(validPasswords.isEmpty());
    }

    @Test
    void testGetValidPasswords_withNullText_shouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordExtractor.getValidPasswords(null);
        });
        assertEquals("Input text cannot be null", exception.getMessage());
    }
}
