package org.example.cp_lab_4.models;

public record ScrapingResult(
        String url,
        String status,
        String content,
        long duration) { }
