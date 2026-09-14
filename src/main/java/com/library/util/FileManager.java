package com.library.util;

import com.library.model.Book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {

    private static final String BOOK_FILE = "data/books.txt";

    public static void saveBooks(List<Book> books) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(BOOK_FILE))) {

            for (Book book : books) {

                writer.write(
                        book.getBookId() + "|" +
                        book.getTitle() + "|" +
                        book.getAuthor() + "|" +
                        book.getIsbn() + "|" +
                        book.getCategory() + "|" +
                        book.getTotalCopies()
                );

                writer.newLine();
            }

            System.out.println("Books saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error while saving books: "
                            + e.getMessage()
            );
        }
    }

    public static void readBooks() {

        System.out.println("\n========== SAVED BOOK DATA ==========");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(BOOK_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {

            System.out.println(
                    "Error while reading books: "
                            + e.getMessage()
            );
        }
    }
}