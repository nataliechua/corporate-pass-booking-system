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

    @ModelAttribute("loan")
    public Loan loan() {
        return new Loan();
    }

    @GetMapping
    public String gopReturnPass(Model model) {
        List<Loan> loans = loanService.getAllLoans(); 
        model.addAttribute("loans", loans);
        return "gopReturnPass";
    }

    @PutMapping("/{id}/{status}")
    public String gopUpdateLoanStatus(@PathVariable("id") Long loanId, @PathVariable("status") String loanStatus) {
        //Loan loan = new Loan();
        Loan loan = loanService.getLoanById(loanId);
        if (loanStatus.equals("not collected")){
            loan.setLoanStatus("collected");
            loanService.updateLoan(loanId, loan);
        }else{
            loan.setLoanStatus("returned");
            loanService.updateLoan(loanId, loan);
        }
        return "redirect:/gopReturnPass";  
    }
    @PutMapping("/{passId}/{loanId}/{type}")
    public String gopUpdateLostPass(@PathVariable("loanId") Long loanId, @PathVariable("passId") Long passId, @PathVariable("type") String updateType) {
        //Loan loan = new Loan();
        passService.reportLostPass(passId, loanId);
        return "redirect:/gopReturnPass";  
    }
}
