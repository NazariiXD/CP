package org.example;

import org.example.interfaces.IPasswordExtractor;
import org.example.services.PasswordExtractor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        IPasswordExtractor passwordExtractor = new PasswordExtractor();

        System.out.print("Введіть шлях до файлу: ");
        var filePath = scanner.nextLine();

        var textContent = readFileContent(filePath);
        var validPasswords = passwordExtractor.getValidPasswords(textContent);

        System.out.println("Знайдені валідні паролі:");
        validPasswords.forEach(System.out::println);
    }

    private static String readFileContent(String filePath) {
        String content = null;

        try {
            content = Files.readString(Path.of(filePath));
        }
        catch (Exception ex) {
            System.err.println("Сталася помилка: " + ex.getMessage());
        }

        return content;
    }
}