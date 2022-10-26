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

    @Override
    public Map<String, Integer> getPassAvailabilityByDate(String date) {

        List<Loan> loanList = loanRepository.findByLoanDate(date);
        Map<String, Integer> map = new HashMap<>();

        for (Loan oneLoan : loanList) {
            Set<Pass> passSet = oneLoan.getPassList();
            for (Pass pass : passSet) {
                String type = pass.getPassType();

                if (map.containsKey(type)) {
                    map.put(type, map.get(type) + 1);
                } else {
                    map.put(type, 1);
                }

            }
        }

        return map;
    }

}
