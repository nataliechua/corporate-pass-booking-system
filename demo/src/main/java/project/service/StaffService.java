package project.service;

import java.util.*;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.entity.*;

public interface StaffService extends UserDetailsService {
    public Staff getStaffById(Long staffId);

    public Staff getStaffByEmail(String email); // added this

    public Long getStaffIdFromLogin(); // added this

    public List<String> saveStaff(Staff staff);

    public List<Staff> getAllStaff();

    public Staff updateStaff(Long staffId, Staff staff);

    public List<Loan> getStaffPresentLoans(Long staffId, String date);

    public List<Loan> getStaffPastLoans(Long staffId, String date);

    public void clearFees(Long staffId);

    public Staff saveStaffToDB(Staff staff);

    // public List<Loan> getStaffLoansForAMonthAndAttraction()
}
