package com.library.exception;

public class BookNotAvailableException extends LibraryException {

    public BookNotAvailableException(int bookId) {
        super("Book with ID " + bookId + " has no available copies.");
    }
}