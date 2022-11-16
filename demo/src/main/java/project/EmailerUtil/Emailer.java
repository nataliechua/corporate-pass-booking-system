package project.EmailerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import project.service.*;
import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.repository.*;
import project.util.*;

@RestController
public class Emailer {
    
    @Autowired
    SendEmail emailUtil;

    @GetMapping(path = "/sendSimpleEmail/{toEmail}")
    public String simpleEmail(@PathVariable("toEmail") String receiverEmail) throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo(receiverEmail);//replace with your desired email
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject("Yiling's test");
        emailUtil.sendSimpleEmail(mail,"hello good day");
        return "SUCCESSED";
    }

    //@GetMapping(path = "/sendTemplateEmail/{template}")
    //confirmCollect, confirmReturn, remindCollect, remindReturn
    public String emailWithTemplate(String template, Loan loan) throws IOException, MessagingException {

        Staff staff = loan.getStaff();
        String staffEmail = staff.getStaffEmail();
        String staffName = staff.getStaffName();

        Long loanId = loan.getLoanId();
        String loanDate = loan.getLoanDate();
        String attraction = loan.getAttraction();
        String loanStatus = loan.getLoanStatus();
        Set<Pass> passList = loan.getPassList();
        String strPassList = loan.getPassList().stream().map(p->String.valueOf(p.getPassId())).collect(Collectors.joining(","));
        String saturdayBorrower = loan.getSaturdayBorrower();

        Map<String,String> subjectMap = new HashMap<>();
        subjectMap.put("confirmLoanDigital","Confirmation of Pass(es) Booking");
        subjectMap.put("confirmLoanPhysical","Confirmation of Pass(es) Booking");
        subjectMap.put("confirmCollect","Confirmation of Pass(es) Collection");
        subjectMap.put("confirmReturn","Confirmation of Pass(es) Returned");
        subjectMap.put("remindCollect","Reminder to collect Pass(es)");
        subjectMap.put("remindReturn","Reminder to return Pass(es)");
        subjectMap.put("HRLostCard","To HR: Lost Card");
        
        Email mail = new Email();
        mail.setMailTo(staffEmail);
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject(subjectMap.get(template));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("staffName", staffName);
        model.put("loanId", loanId);
        model.put("attraction", attraction);
        model.put("strPassList", strPassList);
        model.put("loanDate", loanDate);
        mail.setProps(model);
        emailUtil.sendEmailWithTemplate(mail, "/emailTemplates/"+ template);
        return "SUCCESSED";
    }

    //HRLostCard
    public String emailWithTemplate(String template, Loan loan, Pass pass) throws IOException, MessagingException {

        Staff staff = loan.getStaff();
        String staffEmail = staff.getStaffEmail();
        String staffName = staff.getStaffName();

        Long loanId = loan.getLoanId();
        String loanDate = loan.getLoanDate();
        String attraction = loan.getAttraction();
        String loanStatus = loan.getLoanStatus();
        Set<Pass> passList = loan.getPassList();
        String strPassList = loan.getPassList().stream().map(p->String.valueOf(p.getPassId())).collect(Collectors.joining(","));
        String saturdayBorrower = loan.getSaturdayBorrower();

        Long passId = pass.getPassId();
        float replacemenetFee = pass.getReplacementFee();

        Map<String,String> subjectMap = new HashMap<>();
        subjectMap.put("confirmLoanDigital","Confirmation of Pass(es) Booking");
        subjectMap.put("confirmLoanPhysical","Confirmation of Pass(es) Booking");
        subjectMap.put("confirmCollect","Confirmation of Pass(es) Collection");
        subjectMap.put("confirmReturn","Confirmation of Pass(es) Returned");
        subjectMap.put("remindCollect","Reminder to collect Pass(es)");
        subjectMap.put("remindReturn","Reminder to return Pass(es)");
        subjectMap.put("HRLostCard","To HR: Lost Card");
        
        Email mail = new Email();
        mail.setMailTo(staffEmail);
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject(subjectMap.get(template));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("staffName", staffName);
        model.put("loanId", loanId);
        model.put("attraction", attraction);
        model.put("strPassList", strPassList);
        model.put("loanDate", loanDate);
        model.put("lostPassId",passId + "");
        model.put("replacementFee",replacemenetFee +"");
        mail.setProps(model);
        emailUtil.sendEmailWithTemplate(mail, "/emailTemplates/"+ template);
        return "SUCCESSED";
    }

    //@GetMapping(path = "/TemplateAttachmentEmail")
    //confirmLoanDigital, confirmLoanPhysical
    public String emailWithAttachmentTemplate() throws IOException, MessagingException {
            Email mail = new Email();
            mail.setMailTo("nataliechua15@gmail.com");//replace with your desired email
            mail.setFrom("testemailjava91@gmail.com");
            mail.setSubject("test template attachment email");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", "valerie");
            model.put("message", "hi");
            mail.setProps(model);
            emailUtil.sendEmailWithAttachmentTemplate(mail, "SAMPLENEWCorporateLetterTemplateCFOZP_002_");
            return "SUCCESSED";
    }
}
    

