package com.library.service;

import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) {
        repository.addBook(book);
    }

    public Book findBook(int bookId) throws BookNotFoundException {

        Book book = repository.findById(bookId);

        if (book == null) {
            throw new BookNotFoundException(bookId);
        }

        return book;
    }

    public void displayAllBooks() {

        List<Book> books = repository.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("\n========== BOOK LIST ==========");

        for (Book book : books) {
            book.displayBook();
        }
    }

    public void borrowBook(int bookId)
            throws BookNotFoundException, BookNotAvailableException {

        Book book = findBook(bookId);

        if (!book.borrowBook()) {
            throw new BookNotAvailableException(bookId);
        }

        System.out.println("Book borrowed successfully.");
    }

    public void returnBook(int bookId)
            throws BookNotFoundException {

        Book book = findBook(bookId);

        book.returnBook();

        System.out.println("Book returned successfully.");
    }

    public boolean removeBook(int bookId) {

        return repository.removeBook(bookId);
    }

    public int getBookCount() {

        return repository.getBookCount();
    }
}
