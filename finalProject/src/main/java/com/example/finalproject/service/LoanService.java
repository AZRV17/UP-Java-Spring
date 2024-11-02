package com.example.finalproject.service;

import com.example.finalproject.models.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getLoans();

    Loan getLoanById(Long id);

    Loan saveLoan(Loan loan);

    void deleteLoan(Long id);

    Loan updateLoan(Loan loan);
}
