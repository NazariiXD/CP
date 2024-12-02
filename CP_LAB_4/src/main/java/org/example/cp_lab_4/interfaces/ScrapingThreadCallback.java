package org.example.cp_lab_4.interfaces;

import org.example.cp_lab_4.models.ScrapingResult;

import java.time.Instant;

public interface ScrapingThreadCallback {
    void onStart();
    void onProcessing(String url);
    void onCompleteProcessing(ScrapingResult result);
    void onFinish(Instant startTime);
}
