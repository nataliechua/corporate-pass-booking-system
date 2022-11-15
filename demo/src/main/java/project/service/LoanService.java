package project.service;

import java.text.ParseException;
import java.util.*;

import project.entity.*;
import project.dto.*;

public interface LoanService {

    public List<Loan> getAllLoans();

    public Loan getLoanById(Long loanId);

    public Loan saveLoan(Loan loan);

    public Loan createNewLoan(LoanRequestDTO loanRequest);

    public List<Loan> getLoansByStaffId(Long loanId);

    public Loan updateLoan(Long loanId, Loan updatedLoan);

    public List<Loan> getLoansByLoanDate(String date);

    public Map<String, Integer> getNumOfPassesByStaffAndMonthAndAttraction(Long staffId, String date);

    public List<Loan> getLoansByStaffAndMonth(Long staffId, String date);

    public void cancelLoanById(Long loanId) throws ParseException; 

    public boolean isDigitalPass(Set<Pass> chosenPasses);
    
}
