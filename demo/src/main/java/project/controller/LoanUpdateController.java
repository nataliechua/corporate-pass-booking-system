package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.service.*;
import project.entity.*;
import project.EmailerUtil.*;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;


@Controller
@RequestMapping("/gopReturnPass")
public class LoanUpdateController {
    
    @Autowired
    private LoanService loanService;
    @Autowired
    private PassService passService;
    @Autowired
    private Emailer emailer;
    
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
    public String gopUpdateLoanStatus(@PathVariable("id") Long loanId, @PathVariable("status") String loanStatus) throws IOException, MessagingException {
        Loan loan = loanService.getLoanById(loanId);

        // If pass has not been collected
        String result = "";
        if (loanStatus.equals("not collected")){
            loan.setLoanStatus("collected");
            loanService.saveLoan(loan);
            result = emailer.emailWithTemplate("confirmCollect", loan);
        } 
        // If pass has been collected
        else {
            loanService.updateSaturdayBorrowersAsReturned(loan);
            result = emailer.emailWithTemplate("confirmReturn", loan);
        }
        return "redirect:/gopReturnPass?success";  
    }
    @PutMapping("/{passId}/{loanId}/{type}")
    public String gopUpdateLostPass(@PathVariable("loanId") Long loanId, @PathVariable("passId") Long passId, @PathVariable("type") String updateType) throws IOException, MessagingException {
        passService.reportLostPass(passId, loanId);
        String result = "";
        Pass pass = passService.getPassById(passId);
        Loan loan = loanService.getLoanById(loanId);
        result = emailer.emailWithTemplate("HRLostCard", loan, pass );
        return "redirect:/gopReturnPass?success";  
    }
}
