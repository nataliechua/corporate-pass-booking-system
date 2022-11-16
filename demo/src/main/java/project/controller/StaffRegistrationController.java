package project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.exception.RegistrationException;
import project.service.*;
import project.util.*;

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

    
    /** 
     * @return Staff
     */
    @ModelAttribute("staff")
    public Staff staff() {
        return new Staff();
    }
    
    
    /** 
     * @return String
     */
    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    
    /** 
     * @param staff
     * @param result
     * @return String
     */
    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("staff") Staff staff, BindingResult result) {

        // first check
        if (result.hasErrors()) { // ensure staff is not empty
            return "register";
        }
        
        staff.setIsAdminHold("FALSE");
        staff.setIsUserActive("FALSE");
        staff.setStaffType("Staff");
        
        List<String> registerResult = new ArrayList<>();
        // final check
        try {
            registerResult = staffService.saveStaff(staff);

        } catch (Exception e) { // Ensure there's no random exception error
            return "redirect:/registration?error"; 
        } 

        if (registerResult.size() > 0) {
            ObjectError error = new ObjectError("globalError", registerResult.get(0));
            result.addError(error);
        }
        if (result.hasErrors()) { // check if there's any error msg from the validation
            return "register";
        }
        return "redirect:/registration?success"; 
    }
}