package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

import java.time.*;
import java.util.*; 

@Controller
@RequestMapping("/loanedPasses")
public class LoanBorrowerController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private StaffService staffService;
    
    public LoanBorrowerController(LoanService loanService) {
        super();
        this.loanService = loanService;
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

    // @PostMapping
    // public String createNewPass(@ModelAttribute("pass") Pass pass) {
    //     //pass.setPassId((long) 11);
    //     pass.setIsPassActive("TRUE");
    //     passService.savePass(pass);
    //     return "redirect:/viewPasses";
    //     //staffDto.setIsAdminHold("FALSE");
    //     //staffDto.setIsUserActive("FALSE");
    //     //staffDto.setStaffType("Staff");

    //     //System.out.println(staffDto.toString()); // *** TODO: Add try-catch exception in this portion
        
    //     //staffService.saveStaff(staffDto); // *** TODO: Ensure there's no duplication of email and contact number?
    //     //return "redirect:/registration?success";
    // }
}
