package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    
    /** 
     * @return List<Pass>
     */
    @GetMapping("/passes")
    public List<Pass> fetchPassList() {
        return passService.getAllPasses();
    }

    
    /** 
     * @param passId
     * @return Pass
     */
    @GetMapping("/passes/{id}")
    // Path variable is input parameter to our method
    public Pass getPassById(@PathVariable("id") Long passId) {
        return passService.getPassById(passId);
    }

    
    /** 
     * @param pass
     * @return Pass
     */
    @PostMapping("/passes")
    public Pass savePass(@RequestBody Pass pass) {
        return passService.savePass(pass);
    }

    
    /** 
     * @param passId
     * @param pass
     * @return Pass
     */
    @PutMapping("/passes/{id}")
    public Pass updatePassById(@PathVariable("id") Long passId, @RequestBody Pass pass) {
        return passService.updatePass(passId, pass);
    }

    
    /** 
     * @return Map<String, Integer>
     */
    @GetMapping("/getTotalPassNum")
    public Map<String, Integer> getTotalPassNum(){
        return passService.getTotalPassNum();
    }

    
    /** 
     * @param passId
     * @param loanId
     */
    @PutMapping("/reportLostPass/{loanId}/{passId}")
    public void reportLostPass(@PathVariable("passId") Long passId, @PathVariable("loanId") Long loanId) {
        passService.reportLostPass(passId, loanId);
    }

    
    /** 
     * @param passId
     */
    @PutMapping("/foundPass/{id}")
    public void foundPass(@PathVariable("id") Long passId) {
        passService.foundPass(passId);
    }

}
