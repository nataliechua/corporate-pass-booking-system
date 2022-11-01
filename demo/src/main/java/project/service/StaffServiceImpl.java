package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff saveStaff(Staff staff) {
        
        Staff staffRecord = new Staff(staff.getStaffName(), staff.getStaffEmail(),
            staff.getContactNum(), new BCryptPasswordEncoder().encode(staff.getPassword()),
            staff.getStaffType());

        System.out.println("===========================");
        System.out.println(staffRecord);
        System.out.println("===========================");

        // *** TODO: Ensure there's no duplication of email and contact number?
        List<Staff> staffDb = getAllStaff();
        
        for (Staff indvStaffDb: staffDb) {
            if (indvStaffDb.getStaffEmail().equals(staff.getStaffEmail())) {
                // cannot insert
                System.out.println("Cannot");
                return null;
            }
            if (indvStaffDb.getContactNum().equals(staff.getContactNum())) {
                // cannot insert
                System.out.println("Cannot");
                return null;
            }
        }
        
        return staffRepository.save(staffRecord);
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
    public Staff getStaffByEmail(String email) { // not sure if we need this
        // .get() to get value of department
        Staff staff = staffRepository.findByStaffEmail(email);

        //    if (!staff.isPresent()) {
        //     throw new DepartmentNotFoundException("Department Not Available");
        //    }

        return staff;
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
    public Staff updateStaffToActive(Long staffId) { // not sure if there's a shorter way to do it
        
        Staff staffDB = staffRepository.findById(staffId).get();
        // *** Return null if staff is already active
        if (staffDB.getIsUserActive().equals("TRUE")) {
            return null;
        }
        
        staffDB.setIsUserActive("TRUE");
        return staffRepository.save(staffDB);
    }

    @Override   
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Staff staff = staffRepository.findByStaffEmail(email);

        if (staff==null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        // staff cannot login if they haven't verify their account
        if (!staff.getIsUserActive().equals("TRUE")) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        UserDetails result = new org.springframework.security.core.userdetails.User(staff.getStaffEmail(), staff.getPassword(), mapRolesToAuthorities(staff.getStaffType()));
        System.out.println("***********************");
        System.out.println(result);
        System.out.println("***********************");
        // AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true
        // org.springframework.security.core.userdetails.User [Username=Sophia, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[Admin]]
        return result;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        return updatedAuthorities;
    }
}
