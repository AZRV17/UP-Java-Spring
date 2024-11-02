package com.example.finalfront.controller;

import com.example.finalfront.models.Loan;
import com.example.finalfront.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public String getAllLoans(Model model) {
        model.addAttribute("loans", loanService.getLoans());
        return "loans/list";
    }

    @GetMapping("/{id}")
    public String getLoan(@PathVariable Long id, Model model) {
        model.addAttribute("loan", loanService.getLoan(id));
        return "loans/details";
    }

    @GetMapping("/create")
    public String createLoanForm(Model model) {
        return "loans/create";
    }

    @GetMapping("/{id}/update")
    public String updateLoanForm(Model model, @PathVariable Long id) {
        model.addAttribute("loan", loanService.getLoan(id));
        return "loans/update";
    }

    @PostMapping
    public String createLoan(Loan loan) {
        loanService.createLoan(loan);
        return "redirect:/loans";
    }

    @PostMapping("/{id}/update")
    public String updateLoan(@PathVariable Long id, Loan loan) {
        loanService.updateLoan(id, loan);
        return "redirect:/loans";
    }

    @PostMapping("/{id}/delete")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }
}
