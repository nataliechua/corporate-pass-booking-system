package project.service;

import java.util.*;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.entity.*;

public interface StaffService extends UserDetailsService {
    public Staff getStaffById(Long staffId);

    public Staff saveStaff(Staff staff);

    public List<Staff> getAllStaff();

    public Staff updateStaff(Long staffId, Staff staff);
}
