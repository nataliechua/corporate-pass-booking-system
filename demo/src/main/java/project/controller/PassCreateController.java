package project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.exception.*;
import java.io.IOException;

import project.entity.*;
import project.service.*;

@Controller
@RequestMapping("/createPass")
public class PassCreateController {
    
    @Autowired
    private PassService passService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private FileUploadController fileUploadController;
    
    public PassCreateController(PassService passService) {
        super();
        this.passService = passService;
    }

    
    /** 
     * @return Pass
     */
    @ModelAttribute("pass")
    public Pass pass() {
        return new Pass();
    }
    
    
    /** 
     * @return String
     */
    @GetMapping
    public String showPassForm() {
        return "adminAddPass";
    }

    
    /** 
     * @param pass
     * @param result
     * @return String
     */
    @PostMapping
    public String createNewPass(@Valid @ModelAttribute("pass") Pass pass, BindingResult result) {
        
        if (result.hasErrors()) { // ensure staff is not empty
            return "adminAddPass";
        }
        
        pass.setIsPassActive("TRUE");
        passService.savePass(pass);
        return "redirect:/viewPasses?success";
      
    }
}
