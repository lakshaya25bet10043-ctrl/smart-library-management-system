package com.library.exception;

public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(int bookId) {
        super("Book with ID " + bookId + " was not found.");
    }
}