package com.library.repository;

import com.library.model.Loan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanRepository {

    private final List<Loan> loans;
    private final Map<Integer, Loan> loanMap;

    public LoanRepository() {

        loans = new ArrayList<>();
        loanMap = new HashMap<>();
    }

    public void addLoan(Loan loan) {

        loans.add(loan);
        loanMap.put(loan.getLoanId(), loan);
    }

    public Loan findById(int loanId) {

        return loanMap.get(loanId);
    }

    public List<Loan> getAllLoans() {

        return new ArrayList<>(loans);
    }
}