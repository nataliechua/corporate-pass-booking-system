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

    
    /** 
     * @return LoanRequestDTO
     */
    @ModelAttribute("loan")
    public LoanRequestDTO loanRequestDTO() {
        return new LoanRequestDTO();
    }

    
    /** 
     * @param model
     * @return String
     */
    @GetMapping
    public String showSelectLoanDate(Model model) {
        Long staffId = staffService.getStaffIdFromLogin();
        Staff staff = staffService.getStaffById(staffId);
        String borrowingStatus = staff.getIsAdminHold();
        model.addAttribute("borrowingStatus", borrowingStatus);
        return "bookAPass";
    }
    

    
    /** 
     * @param dateChosen
     * @param model
     * @param loanRequestDTO
     * @return String
     * @throws ParseException
     */
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

    
    /** 
     * @param dateChosen
     * @param loanRequestDTO
     * @param result
     * @return String
     */
    @PostMapping("/{chosenDate}")
    public String createNewLoan(@PathVariable("chosenDate") String dateChosen, @ModelAttribute("loan") LoanRequestDTO loanRequestDTO, BindingResult result) {
        loanRequestDTO.setLoanDate(dateChosen);
        loanRequestDTO.setStaffId(staffService.getStaffIdFromLogin());
        Loan loan = loanService.createNewLoan(loanRequestDTO);

        if (loan != null){ 
            return "redirect:/loanedPasses?success";  
        }

        return "redirect:/bookAPass/" + dateChosen + "?failed";  
    }

}
