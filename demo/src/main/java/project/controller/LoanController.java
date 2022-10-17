package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import project.entity.*;
import project.service.*;

// import org.springframework.web.bind.annotation.*;
import java.util.*;

// Contains CRUD mapping for the API endpoints

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private PassService passService;


    @GetMapping("/loans")
    public List<Loan> fetchLoanList() {
        return loanService.getAllLoans();
    }

    @GetMapping("/loans/{id}")
    // Path variable is input parameter to our method
    public Loan getLoanById(@PathVariable("id") Long loanId) {
        return loanService.getLoanById(loanId);
    }

    @PostMapping("/loans")
    public Loan saveLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    // @PutMapping("/loans/{loanId}/pass/{passId}")
    // public Loan updateLoanWithPass(@PathVariable("loanId") Long loanId, @PathVariable("passId") Long passId) {
    //     Loan loan = loanService.getLoanById(loanId);
    //     Pass pass = passService.getPassById(passId);

    //     loan.addPassToLoan(pass);

    //     return loanService.saveLoan(loan);
        
    // }

    // @PutMapping("/passes/{id}")
    // public Pass updatePassById(@PathVariable("id") Long passId, @RequestBody Pass pass) {
    //     return passService.updatePass(passId, pass);
    // }
}
