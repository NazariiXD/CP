package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.ScrapingThreadCallback;
import org.example.cp_lab_4.interfaces.UrlHandler;
import org.example.cp_lab_4.models.ScrapingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebScrapingTaskTests {
    private WebScrapingTask webScrapingTask;
    private UrlHandler mockUrlHandler;
    private ScrapingThreadCallback mockCallback;
    private BlockingQueue<String> urlQueue;

    @BeforeEach
    void setUp() {
        urlQueue = new LinkedBlockingQueue<>(10);
        mockUrlHandler = mock(UrlHandler.class);
        mockCallback = mock(ScrapingThreadCallback.class);

        urlQueue.add("https://example.com");

        webScrapingTask = new WebScrapingTask(urlQueue, mockUrlHandler, mockCallback);
    }

    @Test
    void call_whenUrlIsHandledSuccessfully_shouldReturnSuccessfulResult() throws Exception {
        when(mockUrlHandler.handleUrl("https://example.com")).thenReturn("Sample Content");

        List<ScrapingResult> results = webScrapingTask.call();

        assertFalse(results.isEmpty());
        var result = results.get(0);
        assertEquals("https://example.com", result.url());
        assertEquals("Success", result.status());
        assertEquals("Sample Content", result.content());
    }

    @Test
    void call_whenExceptionIsThrownDuringUrlHandling_shouldReturnFailure() throws Exception {
        when(mockUrlHandler.handleUrl("https://example.com")).thenThrow(new RuntimeException("Connection Error"));

        List<ScrapingResult> results = webScrapingTask.call();

        assertFalse(results.isEmpty());
        ScrapingResult result = results.get(0);
        assertEquals("https://example.com", result.url());
        assertEquals("Failed", result.status());
        assertTrue(result.content().contains("Failed: https://example.com"));
    }

    @Test
    void call_whenInterrupted_shouldStopProcessing() {
        urlQueue.add("https://example2.com");

        doAnswer(x -> {
            Thread.currentThread().interrupt();
            return null;
        }).when(mockCallback).onProcessing("https://example.com");

        List<ScrapingResult> results = webScrapingTask.call();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
