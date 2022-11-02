package project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.exception.RegistrationException;
import project.service.*;
import project.util.RegisterUtil;

import java.io.Serializable;
import java.util.*;

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
    public String registerUserAccount(@Valid @ModelAttribute("staff") Staff staff, BindingResult result) {

        // first check
        if (result.hasErrors()) { // ensure staff is not empty
            return "register";
        }

        // second check
        List<String> registerUtilResult = RegisterUtil.validate(staff); // ensure email domain is correct
        
        if (registerUtilResult.size() > 0) {
            ObjectError error = new ObjectError("globalError", registerUtilResult.get(0));
            result.addError(error);
        }

        if (result.hasErrors()) { // ensure staff is not empty
            return "register";
        }
        
        staff.setIsAdminHold("FALSE");
        staff.setIsUserActive("FALSE");
        staff.setStaffType("Staff");
        
        // final check
        try {
            staffService.saveStaff(staff);
        } catch (RegistrationException r) { // Ensure there's no duplication of email and contact number? <backend>
            return "redirect:/registration?error"; 
        } 
        return "redirect:/registration?success"; 
    }
}