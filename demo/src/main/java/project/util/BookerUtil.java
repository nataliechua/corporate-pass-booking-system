package project.util;

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import java.util.*;

import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar.*;

import org.thymeleaf.templateparser.reader.ParserLevelCommentTextReader;

public class BookerUtil {
    public static boolean validate(Loan loan) {
        Date loanDate = null;

        try {
            loanDate = new SimpleDateFormat("yyyy-MM-dd").parse(loan.getLoanDate());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(loanDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -8);
        Date lowerRange = calendar.getTime();

        calendar.setTime(loanDate);
        calendar.add(Calendar.DATE, -1);
        Date upperRange = calendar.getTime();

        if (currentDate.before(lowerRange) || currentDate.after(upperRange)) {
            return false;
        }

        return true;
    }

}
