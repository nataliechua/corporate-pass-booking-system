package project.util;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import java.util.*;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;

public class RegisterUtil {

    // Check email domain
    public static List<String> validate(Staff staff) {

        //String 
        // check if email contains @sportsschool.edu.sg or @nysi.org.sg
        List<String> errorMsg = new ArrayList<String>();

        if (!isEmailDomainValid(staff.getStaffEmail()))
            errorMsg.add("Please register with your company email");
        
        return errorMsg;
    }

    public static boolean isEmailDomainValid(String email) {

        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
        + "(sportsschool\\.edu\\.sg|nysi\\.org\\.sg)$";
        
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
