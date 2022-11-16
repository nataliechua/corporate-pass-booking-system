package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.EmailerUtil.SendEmail;
import project.EmailerUtil.Email;
import project.entity.*;
import project.repository.*;
import project.util.RegisterUtil;
import project.exception.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import java.io.IOException;
import java.text.SimpleDateFormat;  
import java.util.Date;  

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    SendEmail emailUtil;
    @Autowired
    private RegisterUtil registerUtil;

    
    /** 
     * @param staff
     * @return List<String>
     */
    @Override
    public List<String> saveStaff(Staff staff) {
        
        Staff staffRecord = new Staff(staff.getStaffName(), staff.getStaffEmail(),
            staff.getContactNum(), new BCryptPasswordEncoder().encode(staff.getPassword()),
            staff.getStaffType());

        // validation
        List<String> errorMsg = registerUtil.validate(staff); // ensure email domain is correct & account does not exist

        if (errorMsg.size() > 0 ) {
            return errorMsg;
        }

        Staff result = staffRepository.save(staffRecord);

        if (result!=null) {
            try {
                sendVerificationEmail(result.getStaffId(), result.getStaffEmail());
            } catch (IOException e) {
                
            } catch (MessagingException e) {

            } catch (Exception e) {
                
            }
            finally {

            }
        }
        return errorMsg; // empty errorMsg
    }

    
    /** 
     * @param staffId
     * @param staffEmail
     * @throws IOException
     * @throws MessagingException
     */
    private void sendVerificationEmail(Long staffId, String staffEmail) throws IOException, MessagingException {
        Email mail = new Email();
        //mail.setMailTo(staffEmail);
        mail.setMailTo("linpeishann@gmail.com");//TODO: Replace it with the actual email next time but demo use existing email
        mail.setFrom("linpeishann@gmail.com");
        mail.setSubject("Welcome to your new account");
        
        String message = String.format("Thank you for registering your account. \nClick this link: http://localhost:8080/updateStaffToActive/%d to verify your account. \n- Singapore Sports School", staffId);
        emailUtil.sendSimpleEmail(mail, message);
    }

    
    /** 
     * @return List<Staff>
     */
    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    
    /** 
     * @param staffId
     * @return Staff
     */
    @Override
    public Staff getStaffById(Long staffId) {
        // .get() to get value of department
        Optional<Staff> staff = staffRepository.findById(staffId); 

        if (!staff.isPresent()) {
            throw new ObjectNotFoundException("Staff does not exist in the database");
        }

        return staff.get();
    }

    
    /** 
     * @param email
     * @return Staff
     */
    @Override
    public Staff getStaffByEmail(String email) { 
        // .get() to get value of department
        Staff staff = staffRepository.findByStaffEmail(email);

        //    if (!staff.isPresent()) {
        //     throw new DepartmentNotFoundException("Department Not Available");
        //    }

        return staff;
    }

    
    /** 
     * @param UserDetails
     * @return Long
     */
    @Override
    public Long getStaffIdFromLogin() { // not sure if we need this
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        if (email.equals("")) {
            return null;
        }
        Staff result = getStaffByEmail(email);
        System.out.println(result);
        return result.getStaffId();
    }

    
    /** 
     * @param staffId
     * @param staff
     * @return Staff
     */
    @Override
    public Staff updateStaff(Long staffId, Staff staff) {
        // staffDb is the staff obj in database
        // staff is the staff obj w attributes u want to update populated

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


            if (staffDB.getIsUserActive().equals(staff.getIsUserActive())) {
                return null;
            }
            staffDB.setIsUserActive(staff.getIsUserActive());
        }

        // Check if parameters are null
        if (Objects.nonNull(staff.getStaffType()) && !("".equalsIgnoreCase(staff.getStaffType()))) {
            System.out.println("UPDATING STAFF TYPE");
            staffDB.setStaffType(staff.getStaffType());
        }

        return staffRepository.save(staffDB);
    }

    
    /** 
     * @param staffId
     * @param date
     * @return List<Loan>
     */
    @Override
    public Staff saveStaffToDB(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public List<Loan> getStaffPresentLoans(Long staffId, String date) {
        Staff staff = staffRepository.findById(staffId).get();
        List<Loan> loans = staff.getLoans();
        List<Loan> result = new ArrayList<>();

        for (Loan l : loans) {
            String ld = l.getLoanDate();
            if ((ld).equals(date)) {
                result.add(l);
            }
        }
        return result;
    };

    
    /** 
     * @param staffId
     * @param date
     * @return List<Loan>
     */
    @Override
    public List<Loan> getStaffPastLoans(Long staffId, String date) {
        Staff staff = staffRepository.findById(staffId).get();
        List<Loan> loans = staff.getLoans();
        List<Loan> result = new ArrayList<>();

        try {
            Date givenDateObj = new SimpleDateFormat("yyyy-MM-dd").parse(date);  

            for (Loan l : loans) {
                String ld = l.getLoanDate();
                Date loanDateObj = new SimpleDateFormat("yyyy-MM-dd").parse(ld);  
                if ((loanDateObj).before(givenDateObj)) {
                    result.add(l);
                }
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return result;
    };

    
    /** 
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override   
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Staff staff = staffRepository.findByStaffEmail(email);

        if (staff==null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        // staff cannot login if they haven't verify their account
        if (!staff.getIsUserActive().equals("TRUE")) {
            throw new UsernameNotFoundException("Your account has not been verified. Please verify it before you can login.");
        }
        UserDetails result = new org.springframework.security.core.userdetails.User(staff.getStaffEmail(), staff.getPassword(), mapRolesToAuthorities(staff.getStaffType()));
        System.out.println("***********************");
        System.out.println(result);
        System.out.println("***********************");
        return result;
    }

    
    /** 
     * @param staffId
     */
    @Override
    public void clearFees(Long staffId) {
        Staff staff = staffRepository.findById(staffId).get();
        staff.setIsAdminHold("FALSE");
        staffRepository.save(staff);
    }

    
    /** 
     * @param role
     * @return Collection<? extends GrantedAuthority>
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        return updatedAuthorities;
    }
}
