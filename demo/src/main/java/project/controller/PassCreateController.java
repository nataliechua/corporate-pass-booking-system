package project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

@Controller
@RequestMapping("/createPass")
public class PassCreateController {
    
    @Autowired
    private PassService passService;
    
    public PassCreateController(PassService passService) {
        super();
        this.passService = passService;
    }

    @ModelAttribute("pass")
    public Pass pass() {
        return new Pass();
    }
    
    @GetMapping
    public String showPassForm() {
        return "adminAddPass";
    }

    @PostMapping
    public String createNewPass(@Valid @ModelAttribute("pass") Pass pass, BindingResult result) {
        
        if (result.hasErrors()) { // ensure staff is not empty
            return "adminAddPass";
        }
        // check if digitalPath is true/false
        // check pass start date and pass expiry date
        //pass.setPassId((long) 11);
        //=========================================
        pass.setIsPassActive("Active");
        passService.savePass(pass);
        return "redirect:/viewPasses";
        //=========================================
        //staffDto.setIsAdminHold("FALSE");
        //staffDto.setIsUserActive("FALSE");
        //staffDto.setStaffType("Staff");

        //System.out.println(staffDto.toString()); // *** TODO: Add try-catch exception in this portion
        
        //staffService.saveStaff(staffDto); // *** TODO: Ensure there's no duplication of email and contact number?
        //return "redirect:/registration?success";
    }
}
