package org.example.cp_lab_4.interfaces;

import java.util.List;

public interface WebScraper {
    void startScraping(List<String> urls, int numberOfThreads);
    void stopScraping();
}
