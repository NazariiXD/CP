package org.example.cp_lab_4.models;

import javafx.application.Platform;
import javafx.beans.property.*;
import org.example.cp_lab_4.interfaces.ScrapingThreadCallback;

import java.time.Duration;
import java.time.Instant;

public class ThreadStatus implements ScrapingThreadCallback {
    private final String threadName;
    private final StringProperty status;
    private final StringProperty result;
    private final LongProperty totalTime;

    public ThreadStatus(String threadName) {
        this.threadName = threadName;
        this.status = new SimpleStringProperty("Waiting");
        this.result = new SimpleStringProperty("");
        this.totalTime = new SimpleLongProperty(0);
    }

    public String getThreadName() {
        return threadName;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty resultProperty() {
        return result;
    }

    public String getResult() {
        return result.get();
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public LongProperty totalTimeProperty() {
        return totalTime;
    }

    public long getTotalTime() {
        return totalTime.get();
    }

    public void setTotalTime(long totalTime) {
        this.totalTime.set(totalTime);
    }

    @Override
    public void onStart() {
        Platform.runLater(() -> setStatus("Starting"));
    }

    @Override
    public void onProcessing(String url) {
        Platform.runLater(() -> setStatus("Processing: " + url));
    }

    @Override
    public void onCompleteProcessing(ScrapingResult result) {
        Platform.runLater(() -> setResult(result.url() + ": " + result.content().length() + " length, time: " + result.duration() + " ms"));
    }

    @Override
    public void onFinish(Instant startTime) {
        Platform.runLater(() -> {
            setStatus("Completed");
            setTotalTime(Duration.between(startTime, Instant.now()).toMillis());
        });
    }
}
