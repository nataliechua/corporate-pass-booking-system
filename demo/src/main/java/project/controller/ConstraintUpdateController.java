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

    
    /** 
     * @return Constraint
     */
    @ModelAttribute("constraint")
    public Constraint constraint() {
        return new Constraint();
    }
    
    
    /** 
     * @return String
     */
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
    
    
    /** 
     * @param constraintId
     * @param constraintName
     * @param constraint
     * @return String
     */
    @PutMapping("/{id}/{name}")
    public String updateBookingConstraint(@PathVariable("id") Long constraintId, @PathVariable("name") String constraintName, @ModelAttribute("constraints") Constraint constraint) {
        constraint.setConstraintId(constraintId);
        constraint.setConstraintName(constraintName);
        constraintService.updateConstraint(constraint);
        return "redirect:/bookingCriteria?success";
    }
}
