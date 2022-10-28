package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/viewStaffs") 
    public String viewStaffs(Model model) {

        List<Staff> staffs = staffService.getAllStaff(); 
        model.addAttribute("staffs", staffs);
        return "staffs";
    }

    @GetMapping("/viewPasses")
    public String viewPasses(Model model) {
        List<Pass> passes = passService.getAllPasses();
        model.addAttribute("passes", passes);
        return "passes";
    }

    @GetMapping("/gopReturnPass")
    public String gopReturnPass() {
        return "gopReturnPass";
    }

    @GetMapping("/bookAPass")
    public String bookAPass() {
        return "bookAPass";
    }

    @GetMapping("/loanedPasses")
    public String loanedPasses() {
        return "loanedPasses";
    }

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
    
}
