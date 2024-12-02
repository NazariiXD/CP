package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.UrlHandler;
import org.example.cp_lab_4.interfaces.WebScraperCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebScraperServiceTests {
    private WebScraperService webScraperService;

    @BeforeEach
    void setUp() {
        var mockUrlHandler = mock(UrlHandler.class);
        WebScraperCallback mockCallback = mock(WebScraperCallback.class);

        webScraperService = new WebScraperService(mockUrlHandler, mockCallback);
    }

    @Test
    void startScraping_whenUrlsIsNull_shouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            webScraperService.startScraping(null, 2);
        });

        assertEquals("URL list cannot be null or empty", exception.getMessage());
    }

    @Test
    void startScraping_whenUrlsIsEmpty_shouldThrowException() {
        List<String> urls = List.of();
        int numberOfThreads = 2;

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            webScraperService.startScraping(urls, numberOfThreads);
        });

        assertEquals("URL list cannot be null or empty", exception.getMessage());
    }

    @Test
    void startScraping_whenInvalidThreadCount_shouldThrowException() {
        List<String> urls = Arrays.asList("https://example.com", "https://example.org");
        int numberOfThreads = 0;

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            webScraperService.startScraping(urls, numberOfThreads);
        });

        assertEquals("Number of threads must be greater than 0", exception.getMessage());
    }
}
