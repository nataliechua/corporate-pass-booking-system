package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

@Controller
@RequestMapping("/registration")
public class StaffRegistrationController {
    
    @Autowired
    private StaffService staffService;
    
    public StaffRegistrationController(StaffService staffService) {
        super();
        this.staffService = staffService;
    }

    @ModelAttribute("staff")
    public Staff staff() {
        return new Staff();
    }
    
    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("staff") Staff staff) {

        staff.setIsAdminHold("FALSE");
        staff.setIsUserActive("FALSE");
        staff.setStaffType("Staff");

        System.out.println(staff.toString()); // *** TODO: Add try-catch exception in this portion
        
        staffService.saveStaff(staff); // *** TODO: Ensure there's no duplication of email and contact number?
        return "redirect:/registration?success";    
    }
}