package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.service.StaffService;
import project.entity.*;
import project.service.*;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String welcome() {
        return "login";
    }

    @RequestMapping("/viewStaffs") 
    public String viewStaffs(Model model) {

        List<Staff> staffs = staffService.getAllStaff(); 
        model.addAttribute("staffs", staffs);
        return "staffs";
    }

    @GetMapping("/viewPasses")
    public String viewPasses() {
        return "passes";
    }

    @GetMapping("/gopReturnPass")
    public String gopReturnPass() {
        return "gopReturnPass";
    }

    @GetMapping("/bookAPass")
    public String bookAPass() {
        return "bookAPass";
    }

    @GetMapping("/loanedPasses")
    public String loanedPasses() {
        return "loanedPasses";
    }
}
