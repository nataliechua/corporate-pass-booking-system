package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.service.*;
import project.entity.*;

import java.util.*;

@Controller
@RequestMapping("/gopReturnPass")
public class LoanUpdateController {
    
    @Autowired
    private LoanService loanService;
    @Autowired
    private PassService passService;
    
    public LoanUpdateController(LoanService loanService, PassService passService) {
        super();
        this.loanService = loanService;
        this.passService = passService;
    }

    
    /** 
     * @return Loan
     */
    @ModelAttribute("loan")
    public Loan loan() {
        return new Loan();
    }

    
    /** 
     * @param model
     * @return String
     */
    @GetMapping
    public String gopReturnPass(Model model) {
        List<Loan> loans = loanService.getAllLoans(); 
        model.addAttribute("loans", loans);
        return "gopReturnPass";
    }

    
    /** 
     * @param loanId
     * @param loanStatus
     * @return String
     */
    @PutMapping("/{id}/{status}")
    public String gopUpdateLoanStatus(@PathVariable("id") Long loanId, @PathVariable("status") String loanStatus) {
        //Loan loan = new Loan();
        Loan loan = loanService.getLoanById(loanId);

        // If pass has not been collected
        if (loanStatus.equals("not collected")){
            loan.setLoanStatus("collected");
            loanService.saveLoan(loan);
        } 
        // If pass has been collected
        else {
            loanService.updateSaturdayBorrowersAsReturned(loan);
        }
        return "redirect:/gopReturnPass";  
    }
    
    /** 
     * @param loanId
     * @param passId
     * @param updateType
     * @return String
     */
    @PutMapping("/{passId}/{loanId}/{type}")
    public String gopUpdateLostPass(@PathVariable("loanId") Long loanId, @PathVariable("passId") Long passId, @PathVariable("type") String updateType) {
        //Loan loan = new Loan();
        passService.reportLostPass(passId, loanId);
        return "redirect:/gopReturnPass";  
    }
}
