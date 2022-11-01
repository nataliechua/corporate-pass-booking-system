package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.repository.*;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PassRepository passRepository;

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
    // p
    public Loan createNewLoan(LoanRequestDTO loanRequest) {
        String date = loanRequest.getDate();
        String attraction = loanRequest.getPassType();
        int numOfPasses = loanRequest.getNoOfPasses();
        Long staffId = loanRequest.getStaffId();

        // Returns all passes for an attraction e.g. "Singapore Zoo"
        List<Pass> allPassesForAnAttraction = passRepository.findByAttractionsContaining(attraction);

        System.out.println(allPassesForAnAttraction);

        // Get passes on loan for an attraction and date
        List<Loan> loansList = loanRepository.findByLoanDate(date);
        List<Pass> borrowedPassesForAnAttraction = new ArrayList<>();
        

        for (Loan l : loansList) {
            Set<Pass> passes = l.getPassList();

            for (Pass p : passes) {
                if (p.getAttractions().contains(attraction)) {
                    borrowedPassesForAnAttraction.add(p);
                }
            }

        }

        System.out.println(borrowedPassesForAnAttraction);
        List<Pass> availablePasses = new ArrayList<>();

        // Get list of passes not loaned 

        for (Pass p : allPassesForAnAttraction) {
            if (!borrowedPassesForAnAttraction.contains(p)) {
                availablePasses.add(p);
            }
        }

        System.out.println(availablePasses);

        // Perform validation checks
        if (availablePasses.size() < numOfPasses) {
            throw new LoanCreationException("Not enough passes");
        }

        // Choose passes from available passes
        Set<Pass> chosenPasses = new HashSet<>();

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

 

    // @Override
    // public Loan updateLoanStatus(Long loanId, String updatedStatus) {
    //     Loan loan = loanRepository.findById(loanId).get();

    //     loan.setLoanStatus(updatedStatus);

    //     // Returns the updated loan object
    //     return loanRepository.save(loan);
    // }

}
