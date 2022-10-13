package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

// import org.springframework.web.bind.annotation.*;
import java.util.*;

// Contains CRUD mapping for the API endpoints

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/staff")
    public Staff saveStaff(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }

    @GetMapping("/staff")
    public List<Staff> fetchStaffList() {
        return staffService.getAllStaff();
    }

    @GetMapping("/staff/{id}")
    // Path variable is input parameter to our method
    public Staff getStaffById(@PathVariable("id") Long staffId) {
        return staffService.getStaffById(staffId);
    }

    @PutMapping("/staff/{id}")
    public Staff updateStaffById(@PathVariable("id") Long staffId, @RequestBody Staff staff) {
        return staffService.updateStaff(staffId, staff);
    }
}
