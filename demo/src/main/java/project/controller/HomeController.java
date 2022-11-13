package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ConstraintService constraintService;
    @Autowired
    private PassService passService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String welcome() {
        return "login";
    }
    
    @GetMapping("/updateStaffToActive/{id}") 
    public String updateStaffToActive(@PathVariable("id") Long staffId) {
        
        Staff s = new Staff();
        s.setIsUserActive("TRUE");
        Staff result = staffService.updateStaff(staffId, s);
        System.out.println("hello: ");
        System.out.println(result);
        if (result!=null)
            return "verified";

        return "verifiedError";
    }

    // @GetMapping("/viewStaffs") 
    // public String viewStaffs(Model model) {

    //     List<Staff> staffs = staffService.getAllStaff(); 
    //     model.addAttribute("staffs", staffs);
    //     return "staffs";
    // }

    @GetMapping("/viewPasses")
    public String viewPasses(Model model) {
        List<Pass> passes = passService.getAllPasses();
        model.addAttribute("passes", passes);
        return "passes";
    }

    @PutMapping("/viewPasses/{id}")
    public String updatePassInactive(@PathVariable("id")Long passId) {
        //Pass pass = passService.getPassById(passId);
        Pass pass = new Pass();
        pass.setIsPassActive("FALSE");
        passService.updatePass(passId, pass);
        return "redirect:/viewPasses";
        //return "redirect:/registration?success";
    }

    // @GetMapping("/gopReturnPass")
    // public String gopReturnPass() {
    //     return "gopReturnPass";
    // }

    // @GetMapping("/bookAPass")
    // public String bookAPass() {
    //     return "bookAPass";
    // }

    // @GetMapping("/loanedPasses")
    // public String loanedPasses() {
    //     return "loanedPasses";
    // }

    @GetMapping("/viewLoanHistory")
    public String viewLoanHistory(Model model) {
        List<Loan> loans = loanService.getAllLoans();
        model.addAttribute("loans", loans);
        return "adminViewLoanHistory";
    }

    // @GetMapping("/bookingCriteria") 
    // public String bookingCriteria(Model model) {
    //     List<Constraint> constraints = constraintService.getAllConstraint(); 
    //     model.addAttribute("constraints", constraints);
    //     return "bookingCriteria";
    // }

    // @RequestMapping(value="/bookingCriteria", method=(RequestMethod.PUT))
    // public String updateBookingCriteria(Model model, Long constraintId, Constraint constraint){
    //     constraintService.updateConstraintById(constraintId, constraint);
    //     return "bookingCriteria";
    // }

    @GetMapping("/templateList") 
    public String templateList() {
        return "templateList";
    }
    
    @GetMapping("/404") 
    public String errorPage() {
        return "404";
    }
}
