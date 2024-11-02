package com.example.finalproject.controllers;

import com.example.finalproject.models.Loan;
import com.example.finalproject.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanApiController {
    @Qualifier("loanService")
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<Loan>> getLoans() {
        List<Loan> loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    public ResponseEntity<Loan> saveLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanService.saveLoan(loan);
        return ResponseEntity.ok(savedLoan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@RequestBody Loan loan) {
        Loan updatedLoan = loanService.updateLoan(loan);
        return ResponseEntity.ok(updatedLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
