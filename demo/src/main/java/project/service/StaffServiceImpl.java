package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

}
