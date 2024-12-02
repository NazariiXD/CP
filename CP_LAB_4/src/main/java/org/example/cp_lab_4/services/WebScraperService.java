package org.example.cp_lab_4.services;

import org.example.cp_lab_4.interfaces.UrlHandler;
import org.example.cp_lab_4.interfaces.WebScraper;
import org.example.cp_lab_4.interfaces.WebScraperCallback;
import org.example.cp_lab_4.models.ScrapingResult;
import org.example.cp_lab_4.models.ThreadStatus;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;

public class WebScraperService implements WebScraper {
    private ExecutorService executorService;
    private CompletionService<List<ScrapingResult>> completionService;
    private final UrlHandler urlHandler;
    private final WebScraperCallback callback;

    public WebScraperService(UrlHandler urlHandler, WebScraperCallback callback) {
        this.urlHandler = urlHandler;
        this.callback = callback;
    }

    @Override
    public void startScraping(List<String> urls, int numberOfThreads) {
        if (urls == null || urls.isEmpty()) {
            throw new IllegalArgumentException("URL list cannot be null or empty");
        }

        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than 0");
        }

        executorService = Executors.newCachedThreadPool();
        completionService = new ExecutorCompletionService<>(executorService);

        BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>(urls);

        var startTime = Instant.now();

        int taskCount = Math.min(urls.size(), numberOfThreads);
        for (int i = 0; i < taskCount; i++) {
            var panel = new ThreadStatus("Thread-" + (i + 1));
            callback.onNewThreadAdded(panel);
            completionService.submit(new WebScrapingTask(urlQueue, urlHandler, panel));
        }

        startMonitoringThread(taskCount, startTime);
    }

    @Override
    public void stopScraping() {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                callback.onShutdownError("Tasks did not finish in time");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void startMonitoringThread(int taskCount, Instant startTime) {
        var resultThread = new Thread(() -> {
            try {
                for (int i = 0; i < taskCount; i++) {
                    Future<List<ScrapingResult>> future = completionService.take();
                    List<ScrapingResult> result = future.get();

                    callback.onScrapingProgress(result);
                }

                callback.onScrapingComplete(startTime);
            }
            catch (InterruptedException | ExecutionException e) {
                callback.onScrapingError("An error occurred during scraping.");
            }
        });

        resultThread.start();
    }
}
