package project.service;

import java.util.*;

import project.entity.*;
import project.dto.*;

public interface LoanService {

    public List<Loan> getAllLoans();

    public Loan getLoanById(Long loanId);

    public Loan saveLoan(Loan loan);
    
    public Map<String, Integer> getPassAvailabilityByDate(String date);

    public Loan createNewLoan(LoanRequestDTO loanRequest);

    public List<Loan> getLoansByStaffId(Long loanId);

    public Loan updateLoanStatus(Long loanId, String updatedStatus);
    
}
