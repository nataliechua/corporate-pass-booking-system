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
    public void testCreateNewLoan() {

        // TRY TO CREATE NEW LOAN ON THE 11 DEC 2022 FOR SINGAPORE FLYER
        LoanRequestDTO dto = new LoanRequestDTO("2022-12-18", "Singapore Flyer", "Singapore Flyer", 1, 3L);

        Loan createdLoan = loanServiceImpl.createNewLoan(dto);

        System.out.println("Created loan: " + createdLoan);
    }
}
