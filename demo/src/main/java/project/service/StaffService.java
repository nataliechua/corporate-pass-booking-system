package project.service;

import java.util.*;

import project.dto.*;
import project.entity.*;

public interface StaffService{
    public Staff getStaffById(Long staffId);

    public Staff saveStaff(StaffDto staffDto);

    public List<Staff> getAllStaff();

    public Staff updateStaff(Long staffId, Staff staff);
}
