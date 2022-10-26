package project.service;

import java.util.*;

import project.entity.*;

public interface LoanService {

    public List<Loan> getAllLoans();

    public Loan getLoanById(Long loanId);

    public Loan saveLoan(Loan loan);
    
    public Map<String, Integer> getPassAvailabilityByDate(String date);
    
}
