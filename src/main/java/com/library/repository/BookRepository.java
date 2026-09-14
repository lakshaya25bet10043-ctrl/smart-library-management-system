package com.library.repository;

import com.library.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository {

    private final List<Book> books;
    private final Map<Integer, Book> bookMap;

    public BookRepository() {
        books = new ArrayList<>();
        bookMap = new HashMap<>();
    }

    public void addBook(Book book) {

        if (bookMap.containsKey(book.getBookId())) {
            System.out.println("Book ID already exists.");
            return;
        }

        books.add(book);
        bookMap.put(book.getBookId(), book);

        System.out.println("Book added successfully.");
    }

    public Book findById(int bookId) {
        return bookMap.get(bookId);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public boolean removeBook(int bookId) {

        Book book = bookMap.remove(bookId);

        if (book == null) {
            return false;
        }

        books.remove(book);

        return true;
    }

    public int getBookCount() {
        return books.size();
    }
}