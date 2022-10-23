package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;   
import org.springframework.security.core.Authentication;

import project.service.StaffService;
import project.entity.*;
import project.service.*;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;  

@Controller
public class HomeController {

    @Autowired
    private StaffService staffService;

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
    public String viewPasses() {
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
    public String viewLoanHistory() {
        return "adminViewLoanHistory";
    }

    @GetMapping("/bookingCriteria") 
    public String bookingCriteria() {
        return "bookingCriteria";
    }

    @GetMapping("/templateList") 
    public String templateList() {
        return "templateList";
    }
    
    // @RequestMapping(value="/logout", method=RequestMethod.GET)  
    // public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
    //     if (auth != null){      
    //         new SecurityContextLogoutHandler().logout(request, response, auth);  
    //     }  
    //     return "redirect:/";  
    // }  
}
