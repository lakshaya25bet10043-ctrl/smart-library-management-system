package com.library;

import com.library.dao.BookDAO;
import com.library.dao.LoanDAO;
import com.library.dao.MemberDAO;
import com.library.database.DatabaseInitializer;
import com.library.model.Book;
import com.library.model.BookCategory;
import com.library.model.Loan;
import com.library.model.Member;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println(
                "===== SMART LIBRARY - LOAN JDBC TEST ====="
        );

        DatabaseInitializer.initialize();

        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        LoanDAO loanDAO = new LoanDAO();

        try {

            // ====================================
            // CREATE TEST MEMBER
            // ====================================

            Member member =
                    memberDAO.findById(101);

            if (member == null) {

                member = new Member(
                        101,
                        "Rahul",
                        "rahul@gmail.com",
                        "Premium"
                );

                memberDAO.insert(member);
            }

            // ====================================
            // CREATE TEST BOOK
            // ====================================

            Book book =
                    bookDAO.findById(201);

            if (book == null) {

                book = new Book(
                        201,
                        "Effective Java",
                        "Joshua Bloch",
                        "9780134685991",
                        BookCategory.PROGRAMMING,
                        2
                );

                bookDAO.insert(book);
            }

            // ====================================
            // CREATE LOAN
            // ====================================

            System.out.println(
                    "\n===== ISSUE BOOK ====="
            );

            int loanId = loanDAO.findAll().size() + 1;

Loan loan =
        new Loan(
                loanId,
                member,
                book,
                LocalDate.of(
                        2026, 9, 14
                ),
                LocalDate.of(
                        2026, 9, 21
                )
        );

            loanDAO.createLoan(loan);

            // ====================================
            // FIND LOAN
            // ====================================

            System.out.println(
                    "\n===== LOAN DETAILS ====="
            );

            Loan savedLoan =
                    loanDAO.findById(1);

            if (savedLoan != null) {

                savedLoan.displayLoan();
            }

            // ====================================
            // BOOK STATUS
            // ====================================

            System.out.println(
                    "\n===== BOOK AFTER ISSUE ====="
            );

            Book updatedBook =
                    bookDAO.findById(201);

            if (updatedBook != null) {

                updatedBook.displayBook();
            }

            // ====================================
            // RETURN BOOK
            // ====================================

            System.out.println(
                    "\n===== RETURN BOOK ====="
            );

            LocalDate returnDate =
                    LocalDate.of(
                            2026, 9, 24
                    );

            loanDAO.returnLoan(
                    loanId,
                    returnDate
            );

            // ====================================
            // VERIFY LOAN
            // ====================================

            System.out.println(
                    "\n===== AFTER RETURN ====="
            );

            Loan returnedLoan =
                    loanDAO.findById(1);

            if (returnedLoan != null) {

                returnedLoan.displayLoan();
            }

            // ====================================
            // VERIFY BOOK
            // ====================================

            System.out.println(
                    "\n===== BOOK AFTER RETURN ====="
            );

            Book finalBook =
                    bookDAO.findById(201);

            if (finalBook != null) {

                finalBook.displayBook();
            }

            // ====================================
            // ALL LOANS
            // ====================================

            System.out.println(
                    "\n===== ALL LOANS ====="
            );

            List<Loan> loans =
                    loanDAO.findAll();

            for (Loan currentLoan : loans) {

                currentLoan.displayLoan();
            }

        } catch (Exception e) {

            System.out.println(
                    "DATABASE ERROR: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}