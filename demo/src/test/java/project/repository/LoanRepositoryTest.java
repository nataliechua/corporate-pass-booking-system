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

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PassRepository passRepository;

    @Test
    public void printAllLoans() {
        List<Loan> loanList = loanRepository.findAll();
        System.out.println("loanList = " + loanList);
    }

    @Test
    public void createLoan() {
        System.out.println("Find Staff:");
        Staff staff = staffRepository.findById(2L).get();

        Set<Pass> passes = new HashSet<>();

        System.out.println("Find Passes:");
        Pass pass1 = passRepository.findById(1L).get();
        Pass pass2 = passRepository.findById(2L).get();

        passes.add(pass1);
        passes.add(pass2);

        System.out.println(passes);
        System.out.println("Create new loan:");
        Loan loan = new Loan("2022-10-10","Art Science Museum");

        loan.setStaff(staff);
        loan.setPassList(passes);
        staff.getLoans().add(loan);
        
        System.out.println("Save loan: ");
        loanRepository.save(loan);

        List<Loan> loanList = loanRepository.findAll();
        System.out.println("loanList with new = " + loanList);
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
