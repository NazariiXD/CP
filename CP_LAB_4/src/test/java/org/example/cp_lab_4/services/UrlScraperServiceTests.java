package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.UrlHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlScraperServiceTests {
    private final UrlHandler urlHandler = new UrlScraperService();

    @Test
    void handleUrl_shouldReturnContent_whenUrlIsValid() throws IOException, URISyntaxException {
        var mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream("Sample Content".getBytes()));

        var testUrl = "https://example.com";
        UrlScraperService scraperService = spy(new UrlScraperService());
        doReturn(mockConnection).when(scraperService).createConnection(testUrl);

        var content = scraperService.handleUrl(testUrl);
        assertEquals("Sample Content\n", content);
    }

    @Test
    void handleUrl_whenResponseCodeIsNotOK_shouldThrowException() throws IOException, URISyntaxException {
        var mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

        var testUrl = "https://example.com";
        UrlScraperService scraperService = spy(new UrlScraperService());
        doReturn(mockConnection).when(scraperService).createConnection("https://example.com");

        var exception = assertThrows(IOException.class, () -> scraperService.handleUrl(testUrl));
        assertEquals("Unexpected HTTP response: 400", exception.getMessage());
    }

    @Test
    void handleUrl_whenUrlIsInvalid_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> urlHandler.handleUrl("invalid-url"));
    }

    @Test
    void handleUrl_whenUrlIsNull_shouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> urlHandler.handleUrl(null));
        assertEquals("URL cannot be null or empty", exception.getMessage());
    }

    @Test
    void handleUrl_whenUrlIsEmpty_shouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> urlHandler.handleUrl(" "));
        assertEquals("URL cannot be null or empty", exception.getMessage());
    }
}
