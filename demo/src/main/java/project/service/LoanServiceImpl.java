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
        // .get() to get value of department
       Optional<Loan> loan = loanRepository.findById(loanId); 

       //    if (!staff.isPresent()) {
       //     throw new DepartmentNotFoundException("Department Not Available");
       //    }
   
        return loan.get();
    };

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Map<String, Integer> getPassAvailabilityByDate(String date) {

        List<Loan> loanList = loanRepository.findByLoanDate(date);
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

        return map;
    }

    @Override
    // p
    public Loan createNewLoan(LoanRequestDTO loanRequest) {
        String date = loanRequest.getDate();
        String attraction = loanRequest.getAttraction();
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
    public Loan updateLoanStatus(Long loanId, String updatedStatus) {
        Loan loan = loanRepository.findById(loanId).get();

        loan.setLoanStatus(updatedStatus);

        // Returns the updated loan object
        return loanRepository.save(loan);
    }

}
