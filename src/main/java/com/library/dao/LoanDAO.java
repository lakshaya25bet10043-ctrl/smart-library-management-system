package com.library.dao;

import com.library.database.DatabaseConnection;
import com.library.model.Book;
import com.library.model.BookCategory;
import com.library.model.Loan;
import com.library.model.LoanStatus;
import com.library.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // ========================================
    // CREATE LOAN / ISSUE BOOK
    // ========================================

    public void createLoan(Loan loan) throws SQLException {

        String checkBookSql = """
                SELECT available_copies
                FROM books
                WHERE book_id = ?
                """;

        String insertLoanSql = """
                INSERT INTO loans
                (loan_id, member_id, book_id,
                 issue_date, due_date, return_date,
                 fine, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        String updateBookSql = """
                UPDATE books
                SET available_copies = available_copies - 1
                WHERE book_id = ?
                AND available_copies > 0
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                // Check book availability
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     checkBookSql)) {

                    statement.setInt(
                            1,
                            loan.getBook().getBookId()
                    );

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {

                            throw new SQLException(
                                    "Book not found."
                            );
                        }

                        int available =
                                resultSet.getInt(
                                        "available_copies"
                                );

                        if (available <= 0) {

                            throw new SQLException(
                                    "Book has no available copies."
                            );
                        }
                    }
                }

                // Insert loan
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     insertLoanSql)) {

                    statement.setInt(
                            1,
                            loan.getLoanId()
                    );

                    statement.setInt(
                            2,
                            loan.getMember().getId()
                    );

                    statement.setInt(
                            3,
                            loan.getBook().getBookId()
                    );

                    statement.setString(
                            4,
                            loan.getIssueDate().toString()
                    );

                    statement.setString(
                            5,
                            loan.getDueDate().toString()
                    );

                    statement.setString(
                            6,
                            null
                    );

                    statement.setDouble(
                            7,
                            loan.getFine()
                    );

                    statement.setString(
                            8,
                            loan.getStatus().name()
                    );

                    statement.executeUpdate();
                }

                // Reduce available copies
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateBookSql)) {

                    statement.setInt(
                            1,
                            loan.getBook().getBookId()
                    );

                    int rows =
                            statement.executeUpdate();

                    if (rows == 0) {

                        throw new SQLException(
                                "Unable to update book copies."
                        );
                    }
                }

                connection.commit();

                System.out.println(
                        "Loan created successfully!"
                );

            } catch (SQLException e) {

                connection.rollback();

                throw e;

            } finally {

                connection.setAutoCommit(true);
            }
        }
    }

    // ========================================
    // FIND LOAN BY ID
    // ========================================

    public Loan findById(int loanId)
            throws SQLException {

        String sql =
                "SELECT * FROM loans WHERE loan_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, loanId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return createLoanFromResultSet(
                            resultSet
                    );
                }
            }
        }

        return null;
    }

    // ========================================
    // FIND ALL LOANS
    // ========================================

    public List<Loan> findAll()
            throws SQLException {

        List<Loan> loans =
                new ArrayList<>();

        String sql =
                "SELECT * FROM loans ORDER BY loan_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                loans.add(
                        createLoanFromResultSet(
                                resultSet
                        )
                );
            }
        }

        return loans;
    }

    // ========================================
    // RETURN LOAN
    // ========================================

    public void returnLoan(
            int loanId,
            LocalDate returnDate)
            throws SQLException {

        String findLoanSql = """
                SELECT *
                FROM loans
                WHERE loan_id = ?
                """;

        String updateLoanSql = """
                UPDATE loans
                SET return_date = ?,
                    fine = ?,
                    status = ?
                WHERE loan_id = ?
                """;

        String updateBookSql = """
                UPDATE books
                SET available_copies =
                    CASE
                        WHEN available_copies < total_copies
                        THEN available_copies + 1
                        ELSE total_copies
                    END
                WHERE book_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                Loan loan;

                // Find loan
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     findLoanSql)) {

                    statement.setInt(1, loanId);

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {

                            throw new SQLException(
                                    "Loan not found."
                            );
                        }

                        loan =
                                createLoanFromResultSet(
                                        resultSet
                                );
                    }
                }

                // Check already returned
                if (loan.getStatus() ==
                        LoanStatus.RETURNED) {

                    throw new SQLException(
                            "Loan has already been returned."
                    );
                }

                // Use existing Loan business logic
                loan.returnLoan(returnDate);

                // Update loan
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateLoanSql)) {

                    statement.setString(
                            1,
                            loan.getReturnDate().toString()
                    );

                    statement.setDouble(
                            2,
                            loan.getFine()
                    );

                    statement.setString(
                            3,
                            loan.getStatus().name()
                    );

                    statement.setInt(
                            4,
                            loanId
                    );

                    statement.executeUpdate();
                }

                // Increase available copies
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateBookSql)) {

                    statement.setInt(
                            1,
                            loan.getBook().getBookId()
                    );

                    statement.executeUpdate();
                }

                connection.commit();

                System.out.println(
                        "Book returned successfully!"
                );

                System.out.println(
                        "Fine: ₹" + loan.getFine()
                );

            } catch (SQLException e) {

                connection.rollback();

                throw e;

            } finally {

                connection.setAutoCommit(true);
            }
        }
    }

    // ========================================
    // UPDATE OVERDUE STATUS
    // ========================================

    public void updateOverdueStatus(
            LocalDate currentDate)
            throws SQLException {

        String sql = """
                SELECT loan_id, due_date
                FROM loans
                WHERE status = 'ACTIVE'
                """;

        String updateSql = """
                UPDATE loans
                SET status = 'OVERDUE'
                WHERE loan_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            List<Integer> overdueLoans =
                    new ArrayList<>();

            while (resultSet.next()) {

                LocalDate dueDate =
                        LocalDate.parse(
                                resultSet.getString(
                                        "due_date"
                                )
                        );

                if (currentDate.isAfter(dueDate)) {

                    overdueLoans.add(
                            resultSet.getInt("loan_id")
                    );
                }
            }

            try (PreparedStatement updateStatement =
                         connection.prepareStatement(
                                 updateSql)) {

                for (int loanId : overdueLoans) {

                    updateStatement.setInt(
                            1,
                            loanId
                    );

                    updateStatement.executeUpdate();
                }
            }
        }
    }

    // ========================================
    // RESULT SET → LOAN OBJECT
    // ========================================

    private Loan createLoanFromResultSet(
            ResultSet resultSet)
            throws SQLException {

        int loanId =
                resultSet.getInt("loan_id");

        int memberId =
                resultSet.getInt("member_id");

        int bookId =
                resultSet.getInt("book_id");

        LocalDate issueDate =
                LocalDate.parse(
                        resultSet.getString("issue_date")
                );

        LocalDate dueDate =
                LocalDate.parse(
                        resultSet.getString("due_date")
                );

        String returnDateText =
                resultSet.getString("return_date");

        LocalDate returnDate = null;

        if (returnDateText != null) {

            returnDate =
                    LocalDate.parse(returnDateText);
        }

        // Get member
        Member member =
                findMember(memberId);

        // Get book
        Book book =
                findBook(bookId);

        Loan loan =
                new Loan(
                        loanId,
                        member,
                        book,
                        issueDate,
                        dueDate
                );

        // Reconstruct returned loan
        if (returnDate != null) {

            loan.returnLoan(returnDate);

        } else {

            LocalDate today =
                    LocalDate.now();

            loan.checkOverdue(today);
        }

        return loan;
    }

    // ========================================
    // FIND MEMBER
    // ========================================

    private Member findMember(int memberId)
            throws SQLException {

        String sql = """
                SELECT *
                FROM members
                WHERE member_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, memberId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {

                    throw new SQLException(
                            "Member " +
                            memberId +
                            " not found."
                    );
                }

                return new Member(
                        resultSet.getInt("member_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("membership")
                );
            }
        }
    }

    // ========================================
    // FIND BOOK
    // ========================================

    private Book findBook(int bookId)
            throws SQLException {

        String sql = """
                SELECT *
                FROM books
                WHERE book_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, bookId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {

                    throw new SQLException(
                            "Book " +
                            bookId +
                            " not found."
                    );
                }

                BookCategory category =
                        BookCategory.valueOf(
                                resultSet.getString(
                                        "category"
                                )
                        );

                Book book =
                        new Book(
                                resultSet.getInt(
                                        "book_id"
                                ),
                                resultSet.getString(
                                        "title"
                                ),
                                resultSet.getString(
                                        "author"
                                ),
                                resultSet.getString(
                                        "isbn"
                                ),
                                category,
                                resultSet.getInt(
                                        "total_copies"
                                )
                        );

                int total =
                        resultSet.getInt(
                                "total_copies"
                        );

                int available =
                        resultSet.getInt(
                                "available_copies"
                        );

                int borrowed =
                        total - available;

                for (int i = 0;
                     i < borrowed;
                     i++) {

                    book.borrowBook();
                }

                return book;
            }
        }
    }
}