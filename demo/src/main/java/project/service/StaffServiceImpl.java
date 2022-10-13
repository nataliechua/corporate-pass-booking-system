package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import java.util.*;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long staffId) {
        // .get() to get value of department
       Optional<Staff> staff = staffRepository.findById(staffId); 

    //    if (!staff.isPresent()) {
    //     throw new DepartmentNotFoundException("Department Not Available");
    //    }

       return staff.get();
    }

    @Override
    public Staff updateStaff(Long staffId, Staff staff) {
        Staff staffDB = staffRepository.findById(staffId).get();
        System.out.println(Objects.nonNull(staff.getName()));
        System.out.println("".equalsIgnoreCase(staff.getName()));

        // Check if parameters are null
        if (Objects.nonNull(staff.getName()) && !("".equalsIgnoreCase(staff.getName()))) {
            staffDB.setName(staff.getName());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getEmail()) && !("".equalsIgnoreCase(staff.getEmail()))) {
            staffDB.setEmail(staff.getEmail());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getContactNum()) && !("".equalsIgnoreCase(staff.getContactNum()))) {
            staffDB.setContactNum(staff.getContactNum());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getIsAdminHold()) && !("".equalsIgnoreCase(staff.getIsAdminHold()))) {
            staffDB.setIsAdminHold(staff.getIsAdminHold());
        }

                        // Check if parameters are null
        if (Objects.nonNull(staff.getIsUserActive()) && !("".equalsIgnoreCase(staff.getIsUserActive()))) {
            staffDB.setIsUserActive(staff.getIsUserActive());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getStaffType()) && !("".equalsIgnoreCase(staff.getStaffType()))) {
            staffDB.setStaffType(staff.getStaffType());
        }

        return staffRepository.save(staffDB);
    }

}
