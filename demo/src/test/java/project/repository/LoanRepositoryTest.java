package project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        staff.getLoans().add(loan);
        loan.addPasses(passes);
        
        System.out.println("Save loan: ");
        Loan newLoan = loanRepository.save(loan);

        List<Loan> loanList = loanRepository.findAll();
        System.out.println("loanList with new = " + newLoan);
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

    @Test
    public void printPassesByAttractionAndDate() {

        List<Pass> allPassesForAnAttraction = passRepository.findByAttractionsContaining("Singapore Zoo");

        String attraction = "Singapore Zoo";
        List<Loan> loansList = loanRepository.findByLoanDate("2022-10-03");
        List<Pass> borrowedPassesForAnAttraction = new ArrayList<>();
        List<Pass> availablePasses = new ArrayList<>();

        for (Loan l : loansList) {
            Set<Pass> passes = l.getPassList();

            for (Pass p : passes) {
                if (p.getAttractions().contains(attraction)) {
                    borrowedPassesForAnAttraction.add(p);
                }
            }

        }

        for (Pass p : allPassesForAnAttraction) {
            for (Pass borrowedPass : borrowedPassesForAnAttraction) {
                if (!p.equals(borrowedPass)) {
                    availablePasses.add(p);
                }
            }
        }

        String s = "";
        for(Pass p : allPassesForAnAttraction) {
            s += p.getPassId() + ", ";
        }

        System.out.println("all passes for an attraction=" + s);

        String s2 = "";
        for(Pass p : borrowedPassesForAnAttraction) {
            s2 += p.getPassId() + ", ";
        }

        System.out.println("all passes for an attraction=" + s2);

        String s3 = "";
        for(Pass p : availablePasses) {
            s3 += p.getPassId() + ", ";
        }

        System.out.println("all passes for an attraction=" + s3);
    }

    @Test
    public void printLoansByStaffId() {
        System.out.println(loanRepository.findByStaffStaffId(1L)); 
    }

    @Test
    public void printUpdatedLoan() {
        Loan loan = loanRepository.findById(2L).get();

        String status = "collected";

        loan.setLoanStatus(status);

        loanRepository.save(loan);
    }

    @Test
    public void printLoanByStaffAndMonth() {
        List<Loan> loans = loanRepository.findByStaffAndMonth(1L, "2022-11-24");
        System.out.println(loans);
    }

    // @Test
    // public void print() {
    //     List<Loan> loans = loanRepository.findByLoanDateNotDate("2022-10-03");
    //     System.out.println(loans);
    // }
    
    @Test
    public void cancelLoanById() throws ParseException {
        Loan loan = loanRepository.findById(8L).get();

        if ((loan.getLoanStatus()).equals("not collected")) {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date loanDate = parser.parse(loan.getLoanDate());

            Calendar c = Calendar.getInstance();
            c.setTime(loanDate);
            c.add(Calendar.DATE, -1);

            Date oneDayBefore = c.getTime();
            Date currentDate = new Date();

            if (currentDate.before(oneDayBefore)) {
                loan.setLoanStatus("canceled");
                loanRepository.save(loan);
            }
        }
    }
}
