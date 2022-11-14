package project.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.dto.LoanRequestDTO;
import project.entity.*;
import project.util.*;
import project.repository.*;

import java.util.*;

@SpringBootTest
public class BookerUtilTest {
    @Autowired
    private BookerUtil booker;
    @Autowired 
    private PassRepository passRepository;
    @Autowired 
    private StaffRepository staffRepository;
    @Autowired 
    private LoanRepository loanRepository;

    @Test
    public void validateBookerPositiveTest() {
        // Testing passes per month
        LoanRequestDTO l = new LoanRequestDTO("2022-11-25", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-11-25", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest() {
        // Testing passes per month
        LoanRequestDTO l = new LoanRequestDTO("2022-10-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-10-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest2() {
        // Testing date range
        LoanRequestDTO l = new LoanRequestDTO("2022-01-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-01-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest3() {
        // Testing no. of available passes
        LoanRequestDTO l = new LoanRequestDTO("2022-12-11", 
                                "Singapore Flyer", 
                                "Singapore Flyer",
                                2,
                                1L);

        System.out.println(booker.validate("2022-12-11", 
        "Singapore Flyer", 
        "Singapore Flyer",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest4() {
        // Testing no of passes <= 2
        LoanRequestDTO l = new LoanRequestDTO("2022-01-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                3,
                                1L);

        System.out.println(booker.validate("2022-01-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        3,
        1L));
    }

    @Test
    public void checkIfSaturdaySundayBorrowerTest1() {

        // TEST 1: THE LOAN BEING CREATED IS ON A SATURDAY AND ONE OF THE PASSES BEING BORROWED HAS A BORROWER THE NEXT DAY (SUNDAY)

        Loan l1 = new Loan("2022-12-10", "Singapore Flyer");
        Pass p1 = passRepository.findById(8L).get();
        Staff s1 = staffRepository.findById(2L).get();

        Set<Pass> passSet1 = new HashSet<>();

        passSet1.add(p1);
        l1.addPasses(passSet1);

        l1.addStaff(s1);

        // Returns the updated new loan object being created
        booker.checkIfSaturdaySundayBorrower(l1);

        // Save in db
        loanRepository.save(l1);
    }

    @Test
    public void checkIfSaturdaySundayBorrowerTest2() {

       // TEST 2: THE LOAN BEING CREATED IS ON A SUNDAY AND ONE OF THE PASSES BEING BORROWED HAS A BORROWER THE PREVIOUS DAY (SATURDAY)
        
        // Create a new loan on Saturday
        System.out.println("FIRST LOAN CREATED (SATURDAY)");
        Loan l = new Loan("2022-11-12", "Mandai Wildlife Reserve");
        Pass p = passRepository.findById(6L).get();
        Staff s = staffRepository.findById(3L).get();

        Set<Pass> passSet = new HashSet<>();

        passSet.add(p);
        l.addPasses(passSet);

        l.addStaff(s);

        booker.checkIfSaturdaySundayBorrower(l);

        loanRepository.save(l);

        // Create a new loan on Sunday
        System.out.println("SECOND LOAN CREATED (SUNDAY)");
        Loan l2 = new Loan("2022-11-13", "Mandai Wildlife Reserve");
        Pass p2 = passRepository.findById(6L).get();
        Staff s2 = staffRepository.findById(4L).get();

        Set<Pass> passSet2 = new HashSet<>();

        passSet2.add(p2);
        l2.addPasses(passSet2);

        l2.addStaff(s2);

        booker.checkIfSaturdaySundayBorrower(l2);

        loanRepository.save(l2);
    }

    @Test
    public void checkIfSaturdaySundayBorrowerTest3() {

       // TEST 3: THE LOAN BEING CREATED IS ON A SUNDAY AND BOTH OF THE PASSES BEING BORROWED HAS A BORROWER THE PREVIOUS DAY (SATURDAY)
        
        // Create a new loan on Saturday
        System.out.println("FIRST LOAN CREATED (SATURDAY)");
        Loan l = new Loan("2022-11-12", "Mandai Wildlife Reserve");
        Pass p = passRepository.findById(6L).get();
        Staff s = staffRepository.findById(3L).get();

        Set<Pass> passSet = new HashSet<>();

        passSet.add(p);
        l.addPasses(passSet);

        l.addStaff(s);

        booker.checkIfSaturdaySundayBorrower(l);

        loanRepository.save(l);

        // Create another new loan on Saturday
        System.out.println("SECOND LOAN CREATED (SATURDAY)");
        Loan l1 = new Loan("2022-11-12", "Mandai Wildlife Reserve");
        Pass p1 = passRepository.findById(5L).get();
        Staff s1 = staffRepository.findById(7L).get();

        Set<Pass> passSet1 = new HashSet<>();

        passSet1.add(p1);
        l1.addPasses(passSet1);

        l1.addStaff(s1);

        booker.checkIfSaturdaySundayBorrower(l1);

        loanRepository.save(l1);

        // Create a new loan on Sunday
        System.out.println("THIRD LOAN CREATED (SUNDAY)");
        Loan l2 = new Loan("2022-11-13", "Mandai Wildlife Reserve");
        Pass p2 = passRepository.findById(6L).get();
        Pass anotherP2 = passRepository.findById(5L).get();
        Staff s2 = staffRepository.findById(4L).get();

        Set<Pass> passSet2 = new HashSet<>();

        passSet2.add(p2);
        passSet2.add(anotherP2);
        l2.addPasses(passSet2);

        l2.addStaff(s2);

        booker.checkIfSaturdaySundayBorrower(l2);

        loanRepository.save(l2);
    }
}





