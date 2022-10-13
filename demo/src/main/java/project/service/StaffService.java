package project.service;

import java.util.*;

import project.entity.*;

public interface StaffService {
    public Staff getStaffById(Long staffId);

    public Staff saveStaff(Staff staff);

    public List<Staff> getAllStaff();

    public Staff updateStaff(Long staffId, Staff staff);
}
