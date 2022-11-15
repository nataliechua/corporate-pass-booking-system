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
    @Autowired
    private LoanService loanService;

    public PassBookViewController(PassService passService, StaffService staffService, LoanService loanService) {
        super();
        this.passService = passService;
        this.staffService = staffService;
        this.loanService = loanService;
    }

    @ModelAttribute("loan")
    public LoanRequestDTO loanRequestDTO() {
        return new LoanRequestDTO();
    }

    @GetMapping
    public String showSelectLoanDate(Model model) {
        Long staffId = staffService.getStaffIdFromLogin();
        Staff staff = staffService.getStaffById(staffId);
        String borrowingStatus = staff.getIsAdminHold();
        model.addAttribute("borrowingStatus", borrowingStatus);
        return "bookAPass";
    }
    

    @GetMapping("/{chosenDate}")
    public String updateAdminStuff(@PathVariable("chosenDate") String dateChosen, Model model, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO) {
        Map<String, PassDTO> passes = new HashMap<String, PassDTO>();

        if (!dateChosen.equals("")){
            model.addAttribute("selectedDate", dateChosen);
        }
        List<Loan> loans = loanService.getLoansByLoanDate(dateChosen);  
        passes = passService.getPassTypeInfoWithAvailableAndTotalCount(dateChosen);
        model.addAttribute("passDTO", passes);
        model.addAttribute("loans", loans);
        
        return "bookAPass";  
    }

    @PostMapping("/{chosenDate}")
    public String createNewLoan(@PathVariable("chosenDate") String dateChosen, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO) {
        loanRequestDTO.setLoanDate(dateChosen);
        loanRequestDTO.setStaffId(staffService.getStaffIdFromLogin());
        Loan loan = loanService.createNewLoan(loanRequestDTO);
        // boolean loanCreationResult = booker.validate(loanRequestDTO.getLoanDate(), loanRequestDTO.getPassType()
        //     , loanRequestDTO.getAttraction(), loanRequestDTO.getNumOfPasses(), loanRequestDTO.getStaffId()); 

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");

        System.out.println(loanRequestDTO);
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        if (loan != null){
          return "redirect:/loanedPasses";  
        }
        return "redirect:/bookAPass/" + dateChosen + "#book?failed";  
    }

}
