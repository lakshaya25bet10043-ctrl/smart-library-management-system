package com.library.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String createBooksTable = """
                CREATE TABLE IF NOT EXISTS books (
                    book_id INTEGER PRIMARY KEY,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    isbn TEXT UNIQUE NOT NULL,
                    category TEXT NOT NULL,
                    total_copies INTEGER NOT NULL,
                    available_copies INTEGER NOT NULL
                )
                """;

        String createMembersTable = """
                CREATE TABLE IF NOT EXISTS members (
                    member_id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    membership TEXT NOT NULL
                )
                """;

        String createLoansTable = """
                CREATE TABLE IF NOT EXISTS loans (
                    loan_id INTEGER PRIMARY KEY,
                    member_id INTEGER NOT NULL,
                    book_id INTEGER NOT NULL,
                    issue_date TEXT NOT NULL,
                    due_date TEXT NOT NULL,
                    return_date TEXT,
                    fine REAL DEFAULT 0,
                    status TEXT NOT NULL,
                    FOREIGN KEY (member_id)
                        REFERENCES members(member_id),
                    FOREIGN KEY (book_id)
                        REFERENCES books(book_id)
                )
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            statement.execute(createBooksTable);
            statement.execute(createMembersTable);
            statement.execute(createLoansTable);

            System.out.println(
                    "Database initialized successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database initialization failed: "
                            + e.getMessage()
            );
        }
    }
}