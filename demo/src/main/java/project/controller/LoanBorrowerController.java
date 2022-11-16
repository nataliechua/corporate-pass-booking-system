package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

import java.text.ParseException;
import java.time.*;
import java.util.*; 

@Controller
@RequestMapping("/loanedPasses")
public class LoanBorrowerController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private PassService passService;
    @Autowired
    private StaffService staffService;
    
    public LoanBorrowerController(LoanService loanService, PassService passService, StaffService staffService) {
        super();
        this.loanService = loanService;
        this.passService = passService;
        this.staffService = staffService;
    }

    @ModelAttribute("loan")
    public Loan loan() {
        return new Loan();
    }

    @GetMapping
    public String showBorrowerLoans(Model model) {
        List<Loan> loans = loanService.getLoansByStaffId(staffService.getStaffIdFromLogin());  
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("loans", loans);
        return "loanedPasses";
    }

    @PutMapping("/{id}/{status}")
    public String updateLoanStatus(@PathVariable("id") Long id, @PathVariable("status") String updateType) throws ParseException {
        if (updateType.equals("cancel")){
            loanService.cancelLoanById(id);
        }
       // else{
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     System.out.println(id);
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     System.out.println("============================");
        //     passService.reportLostPass(id);
        // }
        return "redirect:/loanedPasses?success";
    }
}
