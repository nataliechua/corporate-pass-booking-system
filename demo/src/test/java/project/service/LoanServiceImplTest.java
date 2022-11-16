package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.dto.LoanRequestDTO;
import project.entity.*;
import java.util.*;

@SpringBootTest
public class LoanServiceImplTest {
    @Autowired
    private LoanService loanServiceImpl;

    @Test
    public void testGetLoanByLoanDate() {
        List<Loan> loanList = loanServiceImpl.getLoansByLoanDate("2022-12-11");

        System.out.println("Loan list=" + loanList);
    }
    
    @Test
    public void testCreateNewLoan() {

        // TRY TO CREATE NEW LOAN ON THE 17 DEC 2022 FOR SINGAPORE FLYER
        LoanRequestDTO dto = new LoanRequestDTO("2022-12-18", "Singapore Flyer", "Singapore Flyer", 1, 3L);

        Loan createdLoan = loanServiceImpl.createNewLoan(dto);

        System.out.println("Created loan: " + createdLoan);
    }

    @Test
    public void testGetLoansByStatusAndPassedLoanDate() {
        // Takes in 'collected' and currentDate 
        List<Loan> loanList = loanServiceImpl.getLoansByStatusAndPassedLoanDate("collected", "2022-11-03");
        
        System.out.println("Loan list=" + loanList);
    }

    @Test
    public void testGetLoansByStatusAndDate() {
        // Takes in 'not collected' as currentDate + 1
        List<Loan> loanList = loanServiceImpl.getLoansByStatusAndDate("not collected", "2022-10-10");
        
        System.out.println("Loan list=" + loanList);
    }

    @Test
    public void testGetAllNonCancelledLoans() {
        // Takes in 'not collected' as currentDate + 1
        List<Loan> loanList = loanServiceImpl.getAllNonCancelledLoans();
        
        System.out.println("Loan list=" + loanList);
    }
}
