package org.example.cp_lab_4.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface UrlHandler {
    String handleUrl(String url) throws IOException, URISyntaxException;
}
