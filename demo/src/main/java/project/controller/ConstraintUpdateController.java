package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.service.*;
import project.entity.*;

import java.util.*;

@Controller
@RequestMapping("/bookingCriteria")
public class ConstraintUpdateController {
    
    @Autowired
    private ConstraintService constraintService;
    
    public ConstraintUpdateController(ConstraintService constraintService) {
        super();
        this.constraintService = constraintService;
    }

    @ModelAttribute("constraint")
    public Constraint constraint() {
        return new Constraint();
    }
    
    // @GetMapping
    // public String showUpdateForm() {
    //     return "bookingCriteria";
    // }

    @GetMapping
    public String bookingCriteria(Model model) {
        List<Constraint> constraints = constraintService.getAllConstraint(); 
        model.addAttribute("constraints", constraints);
        return "bookingCriteria";
    }

    @PutMapping
    public String updateBookingConstraint(@ModelAttribute("constraints") Constraint constraint) {

        System.out.println(constraint.toString());
        constraintService.updateConstraint(constraint);
        return "bookingCriteria";
        
        //return "redirect:/registration?success";
    }
}
