package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.ScrapingThreadCallback;
import org.example.cp_lab_4.interfaces.UrlHandler;
import org.example.cp_lab_4.models.ScrapingResult;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class WebScrapingTask implements Callable<List<ScrapingResult>> {
    private final UrlHandler urlHandler;
    private final BlockingQueue<String> urlQueue;
    private final ScrapingThreadCallback callback;

    public WebScrapingTask(
            BlockingQueue<String> urlQueue,
            UrlHandler urlHandler,
            ScrapingThreadCallback callback) {
        this.urlQueue = urlQueue;
        this.urlHandler = urlHandler;
        this.callback = callback;
    }

    @Override
    public List<ScrapingResult> call() {
        List<ScrapingResult> processedUrls = new ArrayList<>();
        String url;

        callback.onStart();

        var totalStartTime = Instant.now();

        while ((url = urlQueue.poll()) != null && !Thread.currentThread().isInterrupted()) {
            var startTime = Instant.now();

            try {
                callback.onProcessing(url);

                var content = urlHandler.handleUrl(url);
                var duration = Duration.between(startTime, Instant.now()).toMillis();

                var result = new ScrapingResult(url, "Success", content, duration);
                processedUrls.add(result);

                callback.onCompleteProcessing(result);
            }
            catch (Exception e) {
                var content = "Failed: " + url + " - " + e.getMessage();
                var duration = Duration.between(startTime, Instant.now()).toMillis();
                processedUrls.add(new ScrapingResult(url, "Failed", content, duration));
            }
        }

        callback.onFinish(totalStartTime);

        return processedUrls;
    }
}
