package com.library.dao;

import com.library.database.DatabaseConnection;
import com.library.model.Book;
import com.library.model.BookCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // ========================================
    // INSERT
    // ========================================

    public void insert(Book book) throws SQLException {

        String sql = """
                INSERT INTO books
                (book_id, title, author, isbn, category,
                 total_copies, available_copies)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, book.getBookId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getIsbn());
            statement.setString(5, book.getCategory().name());
            statement.setInt(6, book.getTotalCopies());
            statement.setInt(7, book.getAvailableCopies());

            statement.executeUpdate();

            System.out.println(
                    "Book inserted successfully!"
            );
        }
    }

    // ========================================
    // FIND BY ID
    // ========================================

    public Book findById(int bookId)
            throws SQLException {

        String sql =
                "SELECT * FROM books WHERE book_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, bookId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return createBookFromResultSet(
                            resultSet
                    );
                }
            }
        }

        return null;
    }

    // ========================================
    // FIND ALL
    // ========================================

    public List<Book> findAll()
            throws SQLException {

        List<Book> books =
                new ArrayList<>();

        String sql =
                "SELECT * FROM books ORDER BY book_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Book book =
                        createBookFromResultSet(
                                resultSet
                        );

                books.add(book);
            }
        }

        return books;
    }

    // ========================================
    // UPDATE
    // ========================================

    public void update(Book book)
            throws SQLException {

        String sql = """
                UPDATE books
                SET title = ?,
                    author = ?,
                    isbn = ?,
                    category = ?,
                    total_copies = ?,
                    available_copies = ?
                WHERE book_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    book.getTitle()
            );

            statement.setString(
                    2,
                    book.getAuthor()
            );

            statement.setString(
                    3,
                    book.getIsbn()
            );

            statement.setString(
                    4,
                    book.getCategory().name()
            );

            statement.setInt(
                    5,
                    book.getTotalCopies()
            );

            statement.setInt(
                    6,
                    book.getAvailableCopies()
            );

            statement.setInt(
                    7,
                    book.getBookId()
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Book updated successfully!"
                );

            } else {

                System.out.println(
                        "Book not found."
                );
            }
        }
    }

    // ========================================
    // DELETE
    // ========================================

    public void delete(int bookId)
            throws SQLException {

        String sql =
                "DELETE FROM books WHERE book_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, bookId);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Book deleted successfully!"
                );

            } else {

                System.out.println(
                        "Book not found."
                );
            }
        }
    }

    // ========================================
    // RESULT SET → BOOK OBJECT
    // ========================================

    private Book createBookFromResultSet(
            ResultSet resultSet)
            throws SQLException {

        int bookId =
                resultSet.getInt("book_id");

        String title =
                resultSet.getString("title");

        String author =
                resultSet.getString("author");

        String isbn =
                resultSet.getString("isbn");

        BookCategory category =
                BookCategory.valueOf(
                        resultSet.getString("category")
                );

        int totalCopies =
                resultSet.getInt("total_copies");

        int availableCopies =
                resultSet.getInt("available_copies");

        Book book =
                new Book(
                        bookId,
                        title,
                        author,
                        isbn,
                        category,
                        totalCopies
                );

        /*
         * The Book constructor initially makes all
         * copies available.
         *
         * If the database contains fewer available
         * copies, borrow the difference so that the
         * Java object matches the database.
         */

        int borrowedCopies =
                totalCopies - availableCopies;

        for (int i = 0;
             i < borrowedCopies;
             i++) {

            book.borrowBook();
        }

        return book;
    }
}