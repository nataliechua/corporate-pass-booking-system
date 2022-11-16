package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.repository.*;
import project.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import project.EmailerUtil.*;
import java.io.*;
import javax.mail.MessagingException;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private BookerUtil booker;

    @Autowired
    private Emailer emailer;

    
    /** 
     * @return List<Loan>
     */
    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    
    /** 
     * @param loanId
     * @return Loan
     */
    @Override
    public Loan getLoanById(Long loanId) {
       Optional<Loan> loan = loanRepository.findById(loanId); 

        if (!loan.isPresent()) {
            throw new ObjectNotFoundException("Loan does not exist in the database");
        }
   
        return loan.get();
    };

    
    /** 
     * @param loan
     * @return Loan
     */
    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    
    /** 
     * @param loanRequest
     * @return Loan
     */
    @Override
    public Loan createNewLoan(LoanRequestDTO loanRequest) {
        String date = loanRequest.getLoanDate();
        String passType = loanRequest.getPassType();
        String attraction = loanRequest.getAttraction();
        int numOfPasses = loanRequest.getNumOfPasses();
        Long staffId = loanRequest.getStaffId();

        // Perform validation checks
        boolean isValid = booker.validate(date, passType, attraction, numOfPasses, staffId);

        if (!isValid) {
            return null;
        }

        System.out.println("PASSES VALIDATION CHECKS");

        // Choose passes from available passes
        Set<Pass> chosenPasses = new HashSet<>();
        List<Pass> availablePasses = passRepository.findAvailablePassesForPassTypeAndDate(passType, date);

        System.out.println("AVAILABLE PASSES: " + availablePasses);

        for (int i=0; i<numOfPasses;i++) {
            chosenPasses.add(availablePasses.get(i));
        }

        System.out.println("CHOSEN PASSES: " + chosenPasses);

        // Create new loan
        Staff staff = staffRepository.findById(staffId).get();

        System.out.println("Create new loan:");
        Loan loan = new Loan(date, attraction);

        loan.setStaff(staff);
        // staff.getLoans().add(loan);
        loan.addPasses(chosenPasses);

        if(isDigitalPass(chosenPasses)) {
            loan.setLoanStatus("returned");
        };
        System.out.println("LOAN OBJ: " + loan);

        // Check if a person borrowed the pass the previous day
        // will return null if there is no sunday loan to update
        Loan sundayLoan = booker.checkIfSaturdaySundayBorrower(loan);

        // Save in db and generate loan id
        loanRepository.save(loan);

        if (sundayLoan != null) {
            // add the generated loan id to the saturday borrower property of sunday loan
            sundayLoan.addSaturdayBorrower(":" + loan.getLoanId() + ";");
            loanRepository.save(sundayLoan);
        }

        System.out.println("FINAL LOAN OBJ: " + loan);

        // Send booking confirmation
        Pass[] chosenPassesArray = chosenPasses.toArray(new Pass [chosenPasses.size()]);
        String isDigital = chosenPassesArray[0].getIsDigital();

        String templateName = "";
        if (isDigital.equals("TRUE")){
            templateName = "confirmLoanDigital";
        } else {
            templateName = "confirmLoanPhysical";
        }

        try {
            emailer.emailWithTemplate(templateName, loan);
        } catch (IOException | MessagingException e) {
            System.out.println("There was an exception. Email failed to send.");
        }
        

        return loan;
    };

    
    /** 
     * @param chosenPasses
     * @return boolean
     */
    @Override
    public boolean isDigitalPass(Set<Pass> chosenPasses) {
        boolean isDigitalPass = false;
        for(Pass pass:chosenPasses) {
            if((pass.getIsDigital()).equals("TRUE")) {
                isDigitalPass = true;
            }
        }
        return isDigitalPass;
    }

    
    /** 
     * @param id
     * @return List<Loan>
     */
    @Override
    public List<Loan> getLoansByStaffId(Long id) {
        return loanRepository.findByStaffStaffId(id);
    }

    
    /** 
     * @param loanId
     * @param updatedLoan
     * @return Loan
     */
    @Override
    public Loan updateLoan(Long loanId, Loan updatedLoan) {
        Loan loanDB = loanRepository.findById(loanId).get();
        
        if (Objects.nonNull(updatedLoan.getStaff())) {
            loanDB.setStaff(updatedLoan.getStaff());
        }

        if (Objects.nonNull(updatedLoan.getLoanDate()) && !("".equalsIgnoreCase(updatedLoan.getLoanDate()))) {
            loanDB.setLoanDate(updatedLoan.getLoanDate());
        }

        if (Objects.nonNull(updatedLoan.getAttraction()) && !("".equalsIgnoreCase(updatedLoan.getAttraction()))) {
            loanDB.setAttraction(updatedLoan.getAttraction());
        }

        if (Objects.nonNull(updatedLoan.getPassList())) {
            loanDB.setPassList(updatedLoan.getPassList());
        }

        if (Objects.nonNull(updatedLoan.getLoanStatus()) && !("".equalsIgnoreCase(updatedLoan.getLoanStatus()))) {
            loanDB.setLoanStatus(updatedLoan.getLoanStatus());
        }

        return loanRepository.save(loanDB);
    }

    
    /** 
     * @param date
     * @return List<Loan>
     */
    @Override
    public List<Loan> getLoansByLoanDate(String date) {
        List<Loan> loans = loanRepository.findByLoanDate(date);
        return loans;
    }

    
    /** 
     * @param staffId
     * @param date
     * @return Map<String, Integer>
     */
    @Override
    public Map<String, Integer> getNumOfPassesByStaffAndMonthAndAttraction(Long staffId, String date) {
        List<Loan> loans = loanRepository.findByStaffAndMonth(staffId, date);
        Map<String, Integer> map = new HashMap<>();

        for (Loan l : loans) {
            if (map.containsKey(l.getAttraction())) {
                map.put(l.getAttraction(), map.get(l.getAttraction()) + 1);
            } else {
                map.put(l.getAttraction(), 1);
            }
        }

        return map;
    }

    
    /** 
     * @param staffId
     * @param date
     * @return List<Loan>
     */
    @Override
    public List<Loan> getLoansByStaffAndMonth(Long staffId, String date) {
        List<Loan> loans = loanRepository.findByStaffAndMonth(staffId, date);

        return loans;
    }


    
    /** 
     * @param loanId
     * @throws ParseException
     */
    @Override
    public void cancelLoanById(Long loanId) throws ParseException {
        Loan loan = loanRepository.findById(loanId).get();

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

    
    /** 
     * @param loanStatus
     * @param date
     * @return List<Loan>
     */
    @Override
    public List<Loan> getLoansByStatusAndPassedLoanDate(String loanStatus, String date) {
        return loanRepository.findLoansByStatusAndPassedLoanDate(loanStatus, date);
    }

    
    /** 
     * @param loanStatus
     * @param date
     * @return List<Loan>
     */
    @Override
    public List<Loan> getLoansByStatusAndDate(String loanStatus, String date) {
        return loanRepository.findLoansByStatusAndDate(loanStatus, date);
    }

    
    /** 
     * @return List<Loan>
     */
    @Override
    public List<Loan> getAllNonCancelledLoans() {
        return loanRepository.findByLoanStatusNot("canceled");
    }

    
    /** 
     * @param loan
     */
    @Override
    public void updateSaturdayBorrowersAsReturned(Loan loan) {
        String saturdayBorrower = loan.getSaturdayBorrower();
        List<Loan> loansToUpdate = new ArrayList<>();

        if (saturdayBorrower!=null) {
            System.out.println(saturdayBorrower);
            String[] borrowers = saturdayBorrower.split(";");

            for (String borrower : borrowers) {
                String saturdayBorrowerLoanIdStr = borrower.split(":")[2];
                Long saturdayBorrowerId = Long.parseLong(saturdayBorrowerLoanIdStr);
                Loan saturdayBorrowerLoan = loanRepository.findById(saturdayBorrowerId).get();

                System.out.println("SATURDAY LOAN: " + saturdayBorrowerLoan);

                loansToUpdate.add(saturdayBorrowerLoan);
                
                // System.out.println("SAVING SAT LOAN: ");
                // loanRepository.saveAndFlush(saturdayBorrowerLoan);
                // System.out.println("PRINT AGAIN AFTER SAVING: " + saturdayBorrowerLoan);
            }
        }

        loansToUpdate.add(loan);

        for (Loan loanToUpdate : loansToUpdate) {
            // loanToUpdate.setLoanStatus("returned");
            loanRepository.updateLoanStatus("returned", loanToUpdate.getLoanId());
            System.out.println(loanToUpdate);
        }

        // loanRepository.saveAll(loansToUpdate);
    }



 

    // @Override
    // public Loan updateLoanStatus(Long loanId, String updatedStatus) {
    //     Loan loan = loanRepository.findById(loanId).get();

    //     loan.setLoanStatus(updatedStatus);

    //     // Returns the updated loan object
    //     return loanRepository.save(loan);
    // }

}
