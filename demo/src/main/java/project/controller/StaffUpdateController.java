package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

import java.util.*;

@Controller
@RequestMapping("/viewStaffs")
public class StaffUpdateController {
    
    @Autowired
    private StaffService staffService;
    
    public StaffUpdateController(StaffService staffService) {
        super();
        this.staffService = staffService;
    }

    @ModelAttribute("staff")
    public Staff staff() {
        return new Staff();
    }
    
    @GetMapping
    public String viewStaffs(Model model) {
        List<Staff> staffs = staffService.getAllStaff(); 
        model.addAttribute("staffs", staffs);
        return "staffs";
    }

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
            staffService.updateStaff(staffId, staff);
        }
        
        return "redirect:/viewStaffs?success";  
    }
}