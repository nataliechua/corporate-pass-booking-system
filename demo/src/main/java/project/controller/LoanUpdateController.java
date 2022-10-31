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

    // @PutMapping("/{id}/{type}")
    // public String updateLoanStatus(@PathVariable("id") Long staffId, @PathVariable("type") String updateType) {
    //     Staff staff = staffService.getStaffById(staffId);
    //     if (updateType.equals("addAdmin")){
    //        staff.setStaffType("Admin"); 
    //     }else if(updateType.equals("removeAdmin")){
    //         staff.setStaffType("Staff");
    //     }else if(updateType.equals("activateUser")){
    //         staff.setIsUserActive("TRUE");
    //     }else{
    //         staff.setIsUserActive("FALSE");
    //     }
    //     staffService.updateStaff(staffId, staff);
    //     return "redirect:/viewStaffs";  
    // }
}
