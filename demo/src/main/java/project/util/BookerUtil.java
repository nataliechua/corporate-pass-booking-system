package project.util;

import org.springframework.beans.factory.annotation.Autowired;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import project.repository.*;
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
    @Autowired
    private LoanRepository loanRepository;

    
    /** 
     * @param date
     * @param passType
     * @param attraction
     * @param numOfPasses
     * @param staffId
     * @return boolean
     */
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
            System.out.println("Not in range");
            return false;
        }

        // Check available passes
        List<Pass> availablePasses = passService.getAvailablePassesForPassTypeAndDate(passType, date);

        if (availablePasses.size() == 0) {
            System.out.println("No available passes");
            return false;
        }

        // Check constraints
        Integer passPerLoanPerDay = constraintService.getConstraintByConstraintName("Pass_Per_Loan_Per_Day").getConstraintData();
        Integer loanPerMonth = constraintService.getConstraintByConstraintName("Loan_Per_Month").getConstraintData();
        Integer passPerMonthPerStaffPerAttraction = constraintService.getConstraintByConstraintName("Pass_Per_Month_Per_Staff_Per_Attraction").getConstraintData();
        
        // Check 2 pass per loan
        if (passPerLoanPerDay != null && numOfPasses > passPerLoanPerDay) {
            System.out.println("Does not fulfil 2 pass per loan");
            return false;
        }

        // Check 2 loan per month
        List<Loan> prevLoans = loanService.getLoansByStaffAndMonth(staffId, date);

        if (loanPerMonth != null && prevLoans.size() == loanPerMonth) {
            System.out.println("Does not fulfil 2 loan per month");
            return false;
        }

        // Check 2 loan per staff per month per attraction
        Map<String, Integer> map = loanService.getNumOfPassesByStaffAndMonthAndAttraction(staffId, date);
        
        if (passPerMonthPerStaffPerAttraction != null && map.get(attraction) == passPerMonthPerStaffPerAttraction) {
            System.out.println("Does not fulfil 2 loan per staff per month per attraction");
            return false;
        }

        return true;
    }

    
    /** 
     * @param loan
     * @return Loan
     */
    public Loan checkIfSaturdaySundayBorrower(Loan loan) {
        String date = loan.getLoanDate();
        Set<Pass> passes = loan.getPassList();

        Date loanDate = null;

        try {
            loanDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(loanDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);

        for (Pass p : passes) {

            // If booking is made on Sunday, then check if there is a borrower for that pass on Saturday
            if (dayOfWeek == 1) {
                System.out.println("IT IS A SUNDAY");
                
                c = Calendar.getInstance();
                c.setTime(loanDate);
                c.add(Calendar.DATE, -1);
                Date prevDay = c.getTime();

                String strDate = new SimpleDateFormat("yyyy-MM-dd").format(prevDay); 

                Loan l = passService.getLoanByPassAndDate(p, strDate); // Saturday borrower

                if (l != null) {

                    System.out.println("THERE IS A LOAN ON SATUDAY FOR PASS: " + p.getPassId());
                    Staff saturdayBorrower = l.getStaff();
                    Long saturdayBorrowerId = saturdayBorrower.getStaffId();

                    String saturdayBorrowerAndPassToCollect = "" + saturdayBorrowerId + ":" + p.getPassId() + ":" + l.getLoanId() + ";";

                    // System.out.println(saturdayBorrowerAndPassToCollect);

                    loan.addSaturdayBorrower(saturdayBorrowerAndPassToCollect);
                }
            }

            // If booking is made on a Saturday, then check if there is a borrower for that pass on Sunday
            if (dayOfWeek == 7) {
                System.out.println("IT IS A SATURDAY");
                
                c = Calendar.getInstance();
                c.setTime(loanDate);
                c.add(Calendar.DATE, 1);
                Date nextDay = c.getTime();

                String strDate = new SimpleDateFormat("yyyy-MM-dd").format(nextDay); 

                Loan l = passService.getLoanByPassAndDate(p, strDate); // Sunday borrower

                if (l != null) {

                    System.out.println("THERE IS A LOAN ON SUNDAY FOR PASS: " + p.getPassId());

                    Staff saturdayBorrower = loan.getStaff();

                    Long saturdayBorrowerId = saturdayBorrower.getStaffId();

                    String saturdayBorrowerAndPassToCollect = "" + saturdayBorrowerId + ":" + p.getPassId();

                    l.addSaturdayBorrower(saturdayBorrowerAndPassToCollect);

                    System.out.println(loanRepository.save(l));

                    // Return sunday loan
                    return l;
                }
            };
        }

        return null; // Could return a Map<Pass:Staff> of information of who is sat borrower & what pass to collect

    }

}
