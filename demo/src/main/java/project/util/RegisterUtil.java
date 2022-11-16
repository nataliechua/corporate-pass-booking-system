package project.util;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import java.util.*;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Component
public class RegisterUtil {

    @Autowired
    private StaffService staffService;

    
    /** 
     * @param staff
     * @return List<String>
     */
    // Check email domain & if there's an existing account
    public List<String> validate(Staff staff) {
        
        // check if email contains @sportsschool.edu.sg or @nysi.org.sg
        List<String> errorMsg = new ArrayList<String>();

        if (!isEmailDomainValid(staff.getStaffEmail()))
            errorMsg.add("Please register with your company email");

        // check if there's an existing account
        List<Staff> staffDb = staffService.getAllStaff();
        Optional<Staff> indvStaffRecord = staffDb.stream()
                                            .filter(
                                                p -> p.getStaffEmail().equals(staff.getStaffEmail()) || p.getContactNum().equals(staff.getContactNum())
                                            )
                                            .findFirst();
                                        
        if (indvStaffRecord.isPresent())
            errorMsg.add("Staff email and contact number already exist in the database.");

        return errorMsg;
    }

    
    /** 
     * @param email
     * @return boolean
     */
    public boolean isEmailDomainValid(String email) {

        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
        + "(sportsschool\\.edu\\.sg|nysi\\.org\\.sg)$";
        
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
