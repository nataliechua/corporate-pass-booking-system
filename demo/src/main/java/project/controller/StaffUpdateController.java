package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;
import project.repository.*;

import java.util.*;

@Controller
@RequestMapping("/viewStaffs")
public class StaffUpdateController {
    
    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;
    
    public StaffUpdateController(StaffService staffService) {
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
     * @param model
     * @return String
     */
    @GetMapping
    public String viewStaffs(Model model) {
        List<Staff> staffs = staffService.getAllStaff(); 
        model.addAttribute("staffs", staffs);
        return "staffs";
    }

    
    /** 
     * @param staffId
     * @param updateType
     * @return String
     */
    @PutMapping("/{id}/{type}")
    public String updateAdminStuff(@PathVariable("id") Long staffId, @PathVariable("type") String updateType) {
        Staff staff = staffService.getStaffById(staffId);
        
        if (updateType.equals("clearFees")){
            staffService.clearFees(staffId);
        }else{
            if (updateType.equals("addAdmin")){
                staff.setStaffType("Admin"); 
            }else if(updateType.equals("removeAdmin")){
                staff.setStaffType("Staff");
            }else if(updateType.equals("activateUser")){
                staff.setIsUserActive("TRUE");
            }
            else{
                staff.setIsUserActive("FALSE");
            }
            
            // staffRepository.save(staff);
            staffService.saveStaffToDB(staff);
        }
        //Staff staff = new Staff();
        
        return "redirect:/viewStaffs?success";  
    }
}