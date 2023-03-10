package project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import project.entity.*;
import java.util.*;

@SpringBootTest
public class PassRepositoryTest {
    @Autowired
    private PassRepository passRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Test
    public void printAllPasses() {
        List<Pass> passList = passRepository.findAll();
        System.out.println("passList = " + passList);
    }

    @Test
    public void printAPassesAttractions() {
        Pass p = passRepository.findById(1L).get();
        String[] result = p.getAttractionList();

        System.out.println(Arrays.toString(result));
    }

    @Test
    public void createAPass() {
        Pass p = new Pass("Art Science Museum",
                        "Star Wars",
                        4,
                        "FALSE", 
                        "", 
                        "2022-10-29", 
                        "2024-01-02", 
                        20.0f, 
                        "TRUE");

        
        passRepository.save(p);

        List<Pass> passList = passRepository.findAll();
        System.out.println("passList = " + passList);
    }

    @Test
    public void getPassesForAnAttraction() {
        List<Pass> passes = passRepository.findByAttractionsContaining("Singapore Zoo");
        System.out.println("passList = " + passes);
    }

    @Test
    public void getAvailablePassesForPassTypeAndDate() {
        List<Pass> passes = passRepository.findAvailablePassesForPassTypeAndDate("Garden By The Bay", "2022-12-11");
        System.out.println("passList = " + passes);

        for (Pass p : passes) {
            System.out.println(p.getPassId());
        }
    }

    @Test
    public void getAvailablePassesForADate() {
        List<Pass> passes = passRepository.findAvailablePassesForADate("2022-12-11");
        System.out.println("passList = " + passes);

        for (Pass p : passes) {
            System.out.println(p.getPassId());
        }
    }

    @Test
    public void getAllActivePasses() {
        List<Pass> passes = passRepository.findActivePasses();
        System.out.println(passes);
    }

    @Test
    public void getPassesLoanedOnADate() {
        List<Pass> passes = passRepository.findPassesLoanedOnADate("2022-12-11");
        System.out.println(passes);

        for (Pass p : passes) {
            System.out.println(p.getPassId());
        }

        // Pass p1 = passRepository.findById(1L).get();
        // System.out.println(p1.getLoans());
    }
    
    @Test
    public void reportLostPass() {

        Loan loan = loanRepository.findById(1L).get();
        Set<Pass> passList = loan.getPassList();
        Staff staff = loan.getStaff();

        for(Pass pass:passList){
            if((pass.getPassId()).equals(1L)) {
                pass.setIsPassActive("Lost");
                passRepository.save(pass);
            }
        }

        // loan.setLoanStatus("lost");
        // loanRepository.save(loan);
        cancelLoanForLostPass(1L);
        
        staff.setIsAdminHold("TRUE");
        staffRepository.save(staff);
    }

    
    /** 
     * @param passId
     */
    public void cancelLoanForLostPass(Long passId) {
        List<Long> loanIdList = loanRepository.findLoanByPass(passId);

        for(Long loanId:loanIdList) {
            Loan loan = loanRepository.findById(loanId).get();
            if ((loan.getLoanStatus()).equals("not collected")) {
                loan.setLoanStatus("canceled");
                loanRepository.save(loan);
            }
        }
    }

    @Test
    public void foundPass() {
        Pass pass = passRepository.findById(1L).get();
        // List<Long> loanIdList = loanRepository.findLoanByPass(4L);

        pass.setIsPassActive("Active");
        passRepository.save(pass);

        // for (Long loanId : loanIdList) {
        //     Loan loan = loanRepository.findById(loanId).get();

        //     loan.setLoanStatus("returned");
        //     loanRepository.save(loan);
        // }
    }

    @Test
    public void getLoanForAPassAndDate() {
        Pass p = passRepository.findById(1L).get();
        Loan loan = passRepository.findLoanForAPassAndDate(p, "2022-10-11");
        System.out.println(loan);
    }

    // @Test
    // public void checkPreviousDayBorrower() {
    //     // String str_date = "2022-10-04";

    //     // DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
    //     // Date date = formatter.parse(str_date);

    //     List<Pass> passes = passRepository.findByLoanDateAndPass("2022-10-03", 1L);

    //     System.out.println("passList=" + passes);
    // }

    // @Test
    // public void printAvailablePassesOnADate() {
    //     // String date = "2020-10-07";
    //     // List<Object> passList = passRepository.getPassByAvailability();
    //     // System.out.println("passList = " + passList);
    // }
}
