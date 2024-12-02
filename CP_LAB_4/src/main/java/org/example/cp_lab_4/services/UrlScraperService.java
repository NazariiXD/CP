package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.UrlHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlScraperService implements UrlHandler {
    private static final int TIMEOUT_SECONDS = 5;

    @Override
    public String handleUrl(String url) throws URISyntaxException, IOException {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        var connection = createConnection(url);
        connection.connect();

        var statusCode = connection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            var errorMessage = connection.getResponseMessage();
            throw new IOException("Unexpected HTTP response: " + statusCode + " - " + errorMessage);
        }

        return readContent(connection);
    }

    protected HttpURLConnection createConnection(String url) throws URISyntaxException, IOException {
        var site = new URI(url).toURL();
        var connection = (HttpURLConnection) site.openConnection();
        connection.setConnectTimeout(TIMEOUT_SECONDS * 1000);
        connection.setReadTimeout(TIMEOUT_SECONDS * 1000);

        return connection;
    }

    private String readContent(HttpURLConnection connection) throws IOException {
        var content = new StringBuilder();
        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
