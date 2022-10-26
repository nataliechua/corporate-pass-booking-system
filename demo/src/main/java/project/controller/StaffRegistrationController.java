package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.dto.*;
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
    public StaffDto staffDto() {
        return new StaffDto();
    }
    
    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("staff") StaffDto staffDto) {

        staffDto.setIsAdminHold("FALSE");
        staffDto.setIsUserActive("FALSE");
        staffDto.setStaffType("Staff");

        System.out.println(staffDto.toString()); // *** TODO: Add try-catch exception in this portion
        
        staffService.saveStaff(staffDto); // *** TODO: Ensure there's no duplication of email and contact number?
        return "redirect:/registration?success";
    }
}
