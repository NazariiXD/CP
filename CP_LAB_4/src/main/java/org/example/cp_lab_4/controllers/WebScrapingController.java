package org.example.cp_lab_4.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.cp_lab_4.interfaces.UrlHandler;
import org.example.cp_lab_4.interfaces.WebScraper;
import org.example.cp_lab_4.interfaces.WebScraperCallback;
import org.example.cp_lab_4.models.ScrapingResult;
import org.example.cp_lab_4.models.ThreadStatus;
import org.example.cp_lab_4.services.UrlScraperService;
import org.example.cp_lab_4.services.WebScraperService;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WebScrapingController implements WebScraperCallback {
    @FXML private TextField urlInput;
    @FXML private ListView<String> urlList;
    @FXML private Spinner<Integer> threadSpinner;

    @FXML private TableView<ScrapingResult> resultTable;
    @FXML private TableColumn<ScrapingResult, String> urlColumn;
    @FXML private TableColumn<ScrapingResult, String> statusColumn;
    @FXML private TableColumn<ScrapingResult, Integer> contentLengthColumn;
    @FXML private TableColumn<ScrapingResult, Long> executionTimeColumn;
    @FXML private TextArea contentTextArea;

    @FXML private TableView<ThreadStatus> threadStatusTable;
    @FXML private TableColumn<ThreadStatus, String> threadNameColumn;
    @FXML private TableColumn<ThreadStatus, String> threadStatusColumn;
    @FXML private TableColumn<ThreadStatus, String> threadResultColumn;
    @FXML private TableColumn<ThreadStatus, Long> threadTotalTimeColumn;

    @FXML private Label totalTimeLabel;
    @FXML private Button startButton;

    private final ObservableList<String> urls = FXCollections.observableArrayList();
    private final ObservableList<ThreadStatus> threadStatuses = FXCollections.observableArrayList();
    private final ObservableList<ScrapingResult> results = FXCollections.observableArrayList();

    private final WebScraper webScraper;
    private boolean isRunning = false;

    public WebScrapingController() {
        UrlHandler handler = new UrlScraperService();
        this.webScraper = new WebScraperService(handler, this);
    }

    @FXML
    public void initialize() {
        urlList.setItems(urls);
        threadStatusTable.setItems(threadStatuses);
        resultTable.setItems(results);

        urlColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().url()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().status()));
        contentLengthColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().content().length()).asObject());
        executionTimeColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().duration()).asObject());

        threadNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getThreadName()));
        threadStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        threadResultColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty());
        threadTotalTimeColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty().asObject());
    }

    @FXML
    private void onAddUrl() {
        var url = urlInput.getText().trim();

        if (url.isEmpty()) {
            showAlert("Error", "URL cannot be empty.", Alert.AlertType.ERROR);
        }
        else {
            urls.add(url);
            urlInput.clear();
        }
    }

    @FXML
    private void onStartButtonClick() {
        if (!isRunning) {
            int numberOfThreads = threadSpinner.getValue();
            List<String> urlsToScrape = new ArrayList<>(urls);

            if (urlsToScrape.isEmpty()) {
                showAlert("No URLs", "Please add URLs to scrape.", Alert.AlertType.ERROR);
                return;
            }

            results.clear();
            threadStatuses.clear();

            webScraper.startScraping(urlsToScrape, numberOfThreads);

            startButton.setText("Stop");
            isRunning = true;
        } else {
            webScraper.stopScraping();

            startButton.setText("Start Scraping");
            isRunning = false;
        }
    }

    @FXML
    private void onRowSelected() {
        var selectedResult = resultTable.getSelectionModel().getSelectedItem();

        if (selectedResult != null) {
            String content = selectedResult.content();

            contentTextArea.setText(content);
        }
    }

    @Override
    public void onNewThreadAdded(ThreadStatus threadStatus) {
        Platform.runLater(() -> threadStatuses.add(threadStatus));
    }

    @Override
    public void onScrapingProgress(List<ScrapingResult> scrapingResults) {
        Platform.runLater(() -> results.addAll(scrapingResults));
    }

    @Override
    public void onScrapingComplete(Instant startTime) {
        Platform.runLater(() -> {
            totalTimeLabel.setText("Total execution time: " + Duration.between(startTime, Instant.now()).toMillis() + " ms");

            urls.clear();

            startButton.setText("Start Scraping");
            isRunning = false;

            showAlert("Success", "Scraping completed successfully!", Alert.AlertType.INFORMATION);
        });
    }

    @Override
    public void onScrapingError(String errorMessage) {
        Platform.runLater(() -> showAlert("Error", errorMessage, Alert.AlertType.ERROR));
    }

    @Override
    public void onShutdownError(String errorMessage) {
        System.out.println(errorMessage);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        var alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}