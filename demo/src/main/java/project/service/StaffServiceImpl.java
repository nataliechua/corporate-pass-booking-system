package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.dto.*;
import project.entity.*;
import project.repository.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff saveStaff(StaffDto staffDto) {
        
        Staff staff = new Staff(staffDto.getStaffName(), staffDto.getStaffEmail(),
            staffDto.getContactNum(), new BCryptPasswordEncoder().encode(staffDto.getPassword()), 
            staffDto.getIsAdminHold(), staffDto.getIsUserActive(), 
            staffDto.getStaffType());
        System.out.println("===========================");
        System.out.println(staff);
        System.out.println("===========================");
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
        System.out.println(Objects.nonNull(staff.getStaffName()));
        System.out.println(Objects.nonNull("".equalsIgnoreCase(staff.getStaffName())));
        

        // Check if parameters are null
        if (Objects.nonNull(staff.getStaffName()) && !("".equalsIgnoreCase(staff.getStaffName()))) {
            System.out.println("hello");
            staffDB.setStaffName(staff.getStaffName());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getStaffEmail()) && !("".equalsIgnoreCase(staff.getStaffEmail()))) {
            staffDB.setStaffEmail(staff.getStaffEmail());
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Staff staff = staffRepository.findByStaffEmail(email);

        if (staff==null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        return new org.springframework.security.core.userdetails.User(staff.getStaffEmail(), staff.getPassword(), mapRolesToAuthorities(staff.getStaffType())) ;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        return updatedAuthorities;
    }
}
