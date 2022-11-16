package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import project.entity.*;
import project.dto.*;
import project.service.*;
import project.exception.*;

import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter;

import java.text.ParseException;
// import org.springframework.web.bind.annotation.*;
import java.util.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import project.util.*;

// Contains CRUD mapping for the API endpoints

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private PassService passService;

    
    /** 
     * @return List<Loan>
     */
    @GetMapping("/loans")
    public List<Loan> fetchLoanList() {
        return loanService.getAllLoans();
    }

    
    /** 
     * @param loanId
     * @return Loan
     */
    @GetMapping("/loans/{id}")
    // Path variable is input parameter to our method
    public Loan getLoanById(@PathVariable("id") Long loanId) {
        return loanService.getLoanById(loanId);
    }

    
    /** 
     * @param loan
     * @return Loan
     */
    @PostMapping("/loans")
    public Loan saveLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    
    /** 
     * @param loanId
     * @throws ParseException
     */
    @PutMapping("/loans/{id}")
    public void cancelLoanById(@PathVariable("id") Long loanId) throws ParseException {
        loanService.cancelLoanById(loanId);
    }

    
    /** 
     * @param response
     * @throws IOException
     */
    @GetMapping("/loans/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=loans_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<Loan> listLoans = loanService.getAllLoans();
         
        LoanExcelExporter excelExporter = new LoanExcelExporter(listLoans);
        
        excelExporter.export(response);    
    } 

    // @PostMapping("/createNewLoan")
    // public ResponseEntity<String> saveLoan(@RequestBody LoanRequestDTO loanRequest) {
    //     Loan newLoan;
        
    //     try {
    //         newLoan = loanService.createNewLoan(loanRequest);
    //     } catch (LoanCreationException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    //     }

    //     // Convert object to Json
    //     ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    //     String json;
        
    //     try {
    //         json = ow.writeValueAsString(newLoan);
            
    //     } catch (JsonProcessingException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
        
    //     return ResponseEntity.status(HttpStatus.OK).body(json);
        
    // }

    // @PutMapping("/loans/{loanId}/pass/{passId}")
    // public Loan updateLoanWithPass(@PathVariable("loanId") Long loanId, @PathVariable("passId") Long passId) {
    //     Loan loan = loanService.getLoanById(loanId);
    //     Pass pass = passService.getPassById(passId);

    //     loan.addPassToLoan(pass);

    //     return loanService.saveLoan(loan);
        
    // }

    // @PutMapping("/passes/{id}")
    // public Pass updatePassById(@PathVariable("id") Long passId, @RequestBody Pass pass) {
    //     return passService.updatePass(passId, pass);
    // }
}
