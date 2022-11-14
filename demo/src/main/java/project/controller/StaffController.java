package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import project.entity.*;
import project.service.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// import org.springframework.web.bind.annotation.*;
import java.util.*;

// Contains CRUD mapping for the API endpoints

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/staff")
    public List<String> saveStaff(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }

    @GetMapping("/staff")
    public List<Staff> fetchStaffList() {
        return staffService.getAllStaff();
    }

    @GetMapping("/staff/{id}")
    public Staff getStaffById(@PathVariable("id") Long staffId) {
        return staffService.getStaffById(staffId);
    }

    @PutMapping("/staff/{id}")
    public Staff updateStaffById(@PathVariable("id") Long staffId, @RequestBody Staff staff) {
        return staffService.updateStaff(staffId, staff);
    }

    @PutMapping("/clearFees/{id}")
    public void clearFees(@PathVariable("id") Long staffId) {
        staffService.clearFees(staffId);
    }
}
