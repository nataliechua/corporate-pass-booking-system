package project.util;

import org.springframework.beans.factory.annotation.Autowired;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import java.util.*;

import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar.*;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


@Component
public class BookerUtil {

    @Autowired
    private PassService passService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ConstraintService constraintService;

    public boolean validate(String date, String passType, String attraction, int numOfPasses, Long staffId) {
        // String date = loanRequest.getLoanDate();
        // String passType = loanRequest.getPassType();
        // String attraction = loanRequest.getAttraction();
        // int numOfPasses = loanRequest.getNumOfPasses();
        // Long staffId = loanRequest.getStaffId();

        // Check date range
        Date loanDate = null;

        try {
            loanDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(loanDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -8);
        Date lowerRange = calendar.getTime();

        calendar.setTime(loanDate);
        calendar.add(Calendar.DATE, -1);
        Date upperRange = calendar.getTime();

        if (currentDate.before(lowerRange) || currentDate.after(upperRange)) {
            return false;
        }

        // Check available passes
        List<Pass> availablePasses = passService.getAvailablePassesForPassTypeAndDate(passType, date);

        if (availablePasses.size() == 0) {
            return false;
        }

        // Check constraints
        Integer passPerLoanPerDay = constraintService.getConstraintByConstraintName("Pass_Per_Loan_Per_Day").getConstraintData();
        Integer loanPerMonth = constraintService.getConstraintByConstraintName("Loan_Per_Month").getConstraintData();
        Integer passPerMonthPerStaffPerAttraction = constraintService.getConstraintByConstraintName("Pass_Per_Month_Per_Staff_Per_Attraction").getConstraintData();
        
        // Check 2 pass per loan
        if (passPerLoanPerDay != null && numOfPasses > passPerLoanPerDay) {
            return false;
        }

        // Check 2 loan per month
        List<Loan> prevLoans = loanService.getLoansByStaffAndMonth(staffId, date);

        if (loanPerMonth != null && prevLoans.size() == loanPerMonth) {
            return false;
        }

        // Check 2 loan per staff per month per attraction
        Map<String, Integer> map = loanService.getNumOfPassesByStaffAndMonthAndAttraction(staffId, date);
        
        if (passPerMonthPerStaffPerAttraction != null && map.get(attraction) == passPerMonthPerStaffPerAttraction) {
            return false;
        }

        return true;
    }

    // public Loan checkIfSaturdaySundayBorrower(Loan loan) {
    //     String date = loan.getLoanDate();
    //     Set<Pass> passes = loan.getPassList();

    //     Date loanDate = null;

    //     try {
    //         loanDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    //     } catch (java.text.ParseException e) {
    //         e.printStackTrace();
    //     }

    //     Calendar c = Calendar.getInstance();
    //     c.setTime(yourDate);
    //     int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    //     if (dayOfWeek == 7) {
    //         for (Pass p : passes) {

    //         }
    //     }
    // }

}
