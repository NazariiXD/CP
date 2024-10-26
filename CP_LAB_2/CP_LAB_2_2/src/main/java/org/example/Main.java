package org.example;

import org.example.interfaces.IEmailExtractor;
import org.example.services.EmailExtractor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IEmailExtractor emailExtractor = new EmailExtractor();
        var scanner = new Scanner(System.in);

        try {
            System.out.print("Введіть шлях до файлу: ");
            var filePath = scanner.nextLine();

            var textContent = Files.readString(Path.of(filePath));
            var validEmails = emailExtractor.getValidEmails(textContent);

            System.out.println("Знайдені валідні електронні адреси:");
            validEmails.forEach(System.out::println);
        }
        catch (Exception ex) {
            System.err.println("Сталася помилка: " + ex.getMessage());
        }
    }
}