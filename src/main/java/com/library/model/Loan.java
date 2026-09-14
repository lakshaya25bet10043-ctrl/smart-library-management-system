package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private int loanId;
    private Member member;
    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private LoanStatus status;

    public Loan(
            int loanId,
            Member member,
            Book book,
            LocalDate issueDate,
            LocalDate dueDate) {

        this.loanId = loanId;
        this.member = member;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = LoanStatus.ACTIVE;
        this.fine = 0.0;
    }

    public int getLoanId() {
        return loanId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void returnLoan(LocalDate returnDate) {

        this.returnDate = returnDate;

        long overdueDays =
                ChronoUnit.DAYS.between(
                        dueDate,
                        returnDate
                );

        if (overdueDays > 0) {

            fine = overdueDays * 5.0;

        } else {

            fine = 0.0;
        }

        status = LoanStatus.RETURNED;
    }

    public void returnLoanOnDate(LocalDate returnDate) {

        this.returnDate = returnDate;

        long overdueDays =
                ChronoUnit.DAYS.between(
                        dueDate,
                        returnDate
                );

        if (overdueDays > 0) {

            fine = overdueDays * 5.0;

        } else {

            fine = 0.0;
        }

        status = LoanStatus.RETURNED;
    }

    public void checkOverdue(LocalDate currentDate) {

        if (status == LoanStatus.ACTIVE &&
                currentDate.isAfter(dueDate)) {

            status = LoanStatus.OVERDUE;

            long overdueDays =
                    ChronoUnit.DAYS.between(
                            dueDate,
                            currentDate
                    );

            fine = overdueDays * 5.0;
        }
    }

    public void displayLoan() {

        System.out.println("----------------------------------------");
        System.out.println("Loan ID     : " + loanId);
        System.out.println("Member      : " + member.getName());
        System.out.println("Book        : " + book.getTitle());
        System.out.println("Issue Date  : " + issueDate);
        System.out.println("Due Date    : " + dueDate);
        System.out.println("Return Date : " + returnDate);
        System.out.println("Fine        : ₹" + fine);
        System.out.println("Status      : " + status);
        System.out.println("----------------------------------------");
    }
}