package project.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    public String updateAdminStuff(@PathVariable("chosenDate") String dateChosen, Model model, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO) throws ParseException {
        Map<String, PassDTO> passes = new HashMap<String, PassDTO>();

        System.out.println("========================================");

        System.out.println(dateChosen);
        System.out.println("========================================");
        // if (!dateChosen.equals("")){
            model.addAttribute("selectedDate", dateChosen);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date today = sdf.parse(java.time.LocalDate.now().toString());
            Date chosenDate = sdf.parse(dateChosen);
            int daysDiff = chosenDate.compareTo(today);

            boolean isValidForBooking = true;
            if (daysDiff < 1){
                isValidForBooking = false;
            }
            model.addAttribute("isValidForBooking", isValidForBooking);
            
        //}
        List<Loan> loans = loanService.getLoansByLoanDate(dateChosen);  
        passes = passService.getPassTypeInfoWithAvailableAndTotalCount(dateChosen);
        List<String> jsonPassList = new ArrayList<>();
        
        for (Map.Entry<String, PassDTO> entry : passes.entrySet()) {

            // System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue().getAttractions());
            // System.out.println("{ 'passType' : " + entry.getKey() + ", 'attractions' : '" + entry.getValue().getAttractions() + "'} ");
            String jsonPassString = "{ \"passType\" : \"" + entry.getKey() + "\", \"attractions\" : \"" + entry.getValue().getAttractions() + "\"} ";
            jsonPassList.add(jsonPassString);
        }

        model.addAttribute("passDTO", passes);
        model.addAttribute("loans", loans);
        model.addAttribute("jsonPassList", jsonPassList);
        
        return "bookAPass";  
    }

    @PostMapping("/{chosenDate}")
    public String createNewLoan(@PathVariable("chosenDate") String dateChosen, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO, BindingResult result) {
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

        // set an error msg: for dynamic msg
        // if (loan == null) {
        //     ObjectError error = new ObjectError("globalError", "Sorry, an error has occurred. Please try again");
        //     result.addError(error);
        // }

        // if (result.hasErrors()) { // check if there's any error msg from the validation
        //     return "bookAPass";
        // }

        if (loan != null){ 
            return "redirect:/loanedPasses";  
        }

        //return "bookAPass";
        return "redirect:/bookAPass/" + dateChosen + "?failed";  
    }

}
