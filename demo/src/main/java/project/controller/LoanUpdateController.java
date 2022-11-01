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
    
    public LoanUpdateController(LoanService loanService) {
        super();
        this.loanService = loanService;
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
        Loan loan = new Loan();
        if (loanStatus.equals("not collected")){
            loan.setLoanStatus("collected");
            loanService.updateLoan(loanId, loan);
        }else{
            loan.setLoanStatus("returned");
            loanService.updateLoan(loanId, loan);
        }
        return "redirect:/gopReturnPass";  
    }
}
