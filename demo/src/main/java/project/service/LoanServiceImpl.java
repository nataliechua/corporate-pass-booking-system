package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long loanId) {
        // .get() to get value of department
       Optional<Loan> loan = loanRepository.findById(loanId); 

       //    if (!staff.isPresent()) {
       //     throw new DepartmentNotFoundException("Department Not Available");
       //    }
   
        return loan.get();
    };

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

}
