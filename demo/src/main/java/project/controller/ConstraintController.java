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

@RestController
public class ConstraintController {
    @Autowired
    private ConstraintService constraintService;

    @RequestMapping("/constraint")
    public List<Constraint> getAllConstraint() {
        return constraintService.getAllConstraint();
    }

    @GetMapping("/constraint/{id}")
    public Constraint getConstraintById(@PathVariable("id") Long constraintId) {
        return constraintService.getConstraintById(constraintId);
    }

    @PutMapping("/constraint/{id}")
    public Constraint updateConstraintById(@PathVariable("id") Long constraintId, @RequestBody Constraint constraint) {
        return constraintService.updateConstraintById(constraintId, constraint);
    }
}
