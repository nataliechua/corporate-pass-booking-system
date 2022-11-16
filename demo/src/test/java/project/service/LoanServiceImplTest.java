package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.repository.*;
import project.dto.LoanRequestDTO;
import project.entity.*;
import java.util.*;

@SpringBootTest
public class LoanServiceImplTest {
    @Autowired
    private LoanService loanServiceImpl;
    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void testGetLoanByLoanDate() {
        List<Loan> loanList = loanServiceImpl.getLoansByLoanDate("2022-12-11");

        System.out.println("Loan list=" + loanList);
    }
    
    @Test
    public void testCreateNewLoan() {

        // TRY TO CREATE NEW LOAN ON THE 17 DEC 2022 FOR SINGAPORE FLYER
        LoanRequestDTO dto = new LoanRequestDTO("2022-12-18", "Mandai Wildlife Reserve", "Singapore Zoo", 1, 4L);

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

    @Test
    public void testUpdateLoanAsReturned() {

        // Loan newLoan = new Loan("2022-12-12", "Singapore Zoo");
        // Staff s = staffRepository.findById(1L).get();
        // Pass p = passRepository.findById(1L).get();

        // newLoan.addStaff(s);
        // newLoan.addPasses(p);
        // newLoan.setSaturdayBorrower("1:3:9;")

        // loanRepository.save(newLoan);
        Loan loan = loanRepository.findById(11L).get();
        loanServiceImpl.updateSaturdayBorrowersAsReturned(loan);
        // loan.setLoanStatus("returned");
        // loanRepository.save(loan);
    }

}
