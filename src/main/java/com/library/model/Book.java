package com.library.model;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private BookCategory category;
    private int totalCopies;
    private int availableCopies;
    private BookStatus status;

    public Book(int bookId, String title, String author, String isbn,
                BookCategory category, int totalCopies) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.status = BookStatus.AVAILABLE;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public BookStatus getStatus() {
        return status;
    }

    public boolean borrowBook() {

        if (availableCopies <= 0) {
            return false;
        }

        availableCopies--;

        if (availableCopies == 0) {
            status = BookStatus.BORROWED;
        }

        return true;
    }

    public void returnBook() {

        if (availableCopies < totalCopies) {
            availableCopies++;
        }

        if (availableCopies > 0) {
            status = BookStatus.AVAILABLE;
        }
    }

    public void displayBook() {

        System.out.println("----------------------------------------");
        System.out.println("Book ID          : " + bookId);
        System.out.println("Title            : " + title);
        System.out.println("Author           : " + author);
        System.out.println("ISBN             : " + isbn);
        System.out.println("Category         : " + category);
        System.out.println("Total Copies     : " + totalCopies);
        System.out.println("Available Copies : " + availableCopies);
        System.out.println("Status           : " + status);
        System.out.println("----------------------------------------");
    }
}