package project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.entity.*;
import java.util.*;

// @SpringBootTest(classes={Loan.class, Pass.class, Staff.class})
@SpringBootTest()

public class LoanRepositoryTest {
    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void printAllLoans() {
        List<Loan> loanList = loanRepository.findAll();
        System.out.println("loanList = " + loanList);
    }

    @Test
    public void printPassesLoanedOnADate() {
        List<Loan> loanList = loanRepository.findByLoanDate("2022-10-07");
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
        
        
        System.out.println(map);
    }

    
}
