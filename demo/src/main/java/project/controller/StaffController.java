package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import project.entity.*;
import project.service.*;

import org.springframework.web.bind.annotation.*;

// Contains CRUD mapping for the API endpoints

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/staff")
    public Staff saveStaff(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }
}
