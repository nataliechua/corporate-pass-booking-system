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

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long loanId) {
       Optional<Loan> loan = loanRepository.findById(loanId); 

        if (!loan.isPresent()) {
            throw new ObjectNotFoundException("Loan does not exist in the database");
        }
   
        return loan.get();
    };

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

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

        // Choose passes from available passes
        Set<Pass> chosenPasses = new HashSet<>();
        List<Pass> availablePasses = passRepository.findAvailablePassesForPassTypeAndDate(passType, date);

        for (int i=0; i<numOfPasses;i++) {
            chosenPasses.add(availablePasses.get(i));
        }

        // Create new loan
        Staff staff = staffRepository.findById(staffId).get();

        System.out.println("Create new loan:");
        Loan loan = new Loan(date, attraction);

        loan.setStaff(staff);
        staff.getLoans().add(loan);
        loan.addPasses(chosenPasses);

        Loan newLoan = loanRepository.save(loan);
        
        // Check if a person borrowed the pass the previous day
        
        System.out.println("Save loan: ");
        return newLoan;
    };

    @Override
    public List<Loan> getLoansByStaffId(Long id) {
        return loanRepository.findByStaffStaffId(id);
    }

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

    @Override
    public List<Loan> getLoansByLoanDate(String date) {
        List<Loan> loans = loanRepository.findByLoanDate(date);
        return loans;
    }

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

    @Override
    public List<Loan> getLoansByStaffAndMonth(Long staffId, String date) {
        List<Loan> loans = loanRepository.findByStaffAndMonth(staffId, date);

        return loans;
    }


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
 

    // @Override
    // public Loan updateLoanStatus(Long loanId, String updatedStatus) {
    //     Loan loan = loanRepository.findById(loanId).get();

    //     loan.setLoanStatus(updatedStatus);

    //     // Returns the updated loan object
    //     return loanRepository.save(loan);
    // }

}
