package com.library.service;

import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.exception.LibraryException;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Member;
import com.library.repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanRepository loanRepository;

    private int nextLoanId = 1;

    public LoanService(
            BookService bookService,
            MemberService memberService,
            LoanRepository loanRepository) {

        this.bookService = bookService;
        this.memberService = memberService;
        this.loanRepository = loanRepository;
    }

    public Loan issueBook(
            int memberId,
            int bookId)
            throws BookNotFoundException,
            BookNotAvailableException,
            MemberNotFoundException {

        Member member = memberService.searchById(memberId);

        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }

        Book book = bookService.findBook(bookId);

        if (!book.borrowBook()) {
            throw new BookNotAvailableException(bookId);
        }

        LocalDate issueDate = LocalDate.now();

        LocalDate dueDate =
                issueDate.plusDays(7);

        Loan loan = new Loan(
                nextLoanId++,
                member,
                book,
                issueDate,
                dueDate
        );

        loanRepository.addLoan(loan);

        return loan;
    }

    public void returnBook(int loanId)
            throws LibraryException {

        Loan loan = loanRepository.findById(loanId);

        if (loan == null) {
            throw new LibraryException(
                    "Loan with ID " + loanId + " was not found."
            );
        }

        if (loan.getStatus().name().equals("RETURNED")) {
            throw new LibraryException(
                    "This book has already been returned."
            );
        }

        loan.returnLoan(LocalDate.now());

        loan.getBook().returnBook();

        System.out.println(
                "Book returned successfully."
        );
    }

    public List<Loan> getAllLoans() {

        return loanRepository.getAllLoans();
    }
}