package org.example.cp_lab_4.interfaces;

import org.example.cp_lab_4.models.ScrapingResult;
import org.example.cp_lab_4.models.ThreadStatus;

import java.time.Instant;
import java.util.List;

public interface WebScraperCallback {
    void onNewThreadAdded(ThreadStatus threadStatus);
    void onScrapingProgress(List<ScrapingResult> results);
    void onScrapingComplete(Instant startTime);
    void onScrapingError(String errorMessage);
    void onShutdownError(String errorMessage);
}
