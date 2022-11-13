package project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;  
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;
import project.dto.*;
import project.util.*;

@Controller
@RequestMapping("/bookAPass")
public class PassBookViewController {
    
    @Autowired
    private PassService passService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private BookerUtil booker;

    public PassBookViewController(PassService passService, StaffService staffService) {
        super();
        this.passService = passService;
        this.staffService = staffService;
    }

    @ModelAttribute("loan")
    public LoanRequestDTO loanRequestDTO() {
        return new LoanRequestDTO();
    }

    @GetMapping
    public String showSelectLoanDate(Model model) {
        return "bookAPass";
    }
    

    @GetMapping("/{chosenDate}")
    public String updateAdminStuff(@PathVariable("chosenDate") String dateChosen, Model model, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO) {
        Map<String, PassDTO> passes = new HashMap<String, PassDTO>();

        if (dateChosen != ""){
            model.addAttribute("selectedDate", dateChosen);
        }
        passes = passService.getPassTypeInfoWithAvailableAndTotalCount(dateChosen);
        model.addAttribute("passDTO", passes);
        
        return "bookAPass";  
    }

    @PostMapping("/{chosenDate}")
    public String createNewLoan(@PathVariable("chosenDate") String dateChosen, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO) {
        loanRequestDTO.setLoanDate(dateChosen);
        loanRequestDTO.setStaffId(staffService.getStaffIdFromLogin());System.out.println(loanRequestDTO);
        boolean loanCreationResult = booker.validate(loanRequestDTO.getLoanDate(), loanRequestDTO.getPassType()
            , loanRequestDTO.getAttraction(), loanRequestDTO.getNumOfPasses(), loanRequestDTO.getStaffId()); 
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println(loanCreationResult);
        System.out.println(loanRequestDTO);
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        if (loanCreationResult){
          return "redirect:/loanedPasses";  
        }
        return "redirect:/bookAPass?failed";  
    }

}
