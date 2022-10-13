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
public class PassController {
    @Autowired
    private PassService passService;

    @GetMapping("/passes")
    public List<Pass> fetchPassList() {
        return passService.getAllPasses();
    }

    @GetMapping("/passes/{id}")
    // Path variable is input parameter to our method
    public Pass getPassById(@PathVariable("id") Long passId) {
        return passService.getPassById(passId);
    }

    @PostMapping("/passes")
    public Pass savePass(@RequestBody Pass pass) {
        return passService.savePass(pass);
    }

    @PutMapping("/passes/{id}")
    public Pass updatePassById(@PathVariable("id") Long passId, @RequestBody Pass pass) {
        return passService.updatePass(passId, pass);
    }
}
