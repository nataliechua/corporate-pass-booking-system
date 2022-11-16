package project.EmailerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

import project.entity.*;

@RestController
public class Emailer {
    
    @Autowired
    SendEmail emailUtil;

    
    /** 
     * @param receiverEmail
     * @return String
     * @throws IOException
     * @throws MessagingException
     */
    @GetMapping(path = "/sendSimpleEmail/{toEmail}")
    public String simpleEmail(@PathVariable("toEmail") String receiverEmail) throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo(receiverEmail);//replace with your desired email
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject("Yiling's test");
        emailUtil.sendSimpleEmail(mail,"hello good day");
        return "SUCCESSED";
    }

    
    /** 
     * @param template
     * @param loan
     * @return String
     * @throws IOException
     * @throws MessagingException
     */
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
        emailUtil.sendEmailWithTemplate(mail, template);
        return "SUCCESSED";
    }

    
    /** 
     * @param template
     * @param loan
     * @param pass
     * @return String
     * @throws IOException
     * @throws MessagingException
     */
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
        emailUtil.sendEmailWithTemplate(mail, template);
        return "SUCCESSED";
    }

    
    /** 
     * @return String
     * @throws IOException
     * @throws MessagingException
     */
    //@GetMapping(path = "/TemplateAttachmentEmail")
    //confirmLoanDigital, confirmLoanPhysical
    public String emailWithAttachmentTemplate(String template, Loan loan) throws IOException, MessagingException {
        Staff staff = loan.getStaff();
        String staffEmail = staff.getStaffEmail();
        String staffName = staff.getStaffName();

        Long loanId = loan.getLoanId();
        String loanDate = loan.getLoanDate();
        String attraction = loan.getAttraction();
        String loanStatus = loan.getLoanStatus();
        Set<Pass> passList = loan.getPassList();
        String strPassList = loan.getPassList().stream().map(p->"P0000000"+String.valueOf(p.getPassId())).collect(Collectors.joining(","));
        String saturdayBorrower = loan.getSaturdayBorrower();

        Map<String,String> subjectMap = new HashMap<>();
        subjectMap.put("confirmLoanDigital","Confirmation of Pass(es) Booking (Digital)");
        subjectMap.put("confirmLoanPhysical","Confirmation of Pass(es) Booking (Physical)");
        subjectMap.put("confirmCollect","Confirmation of Pass(es) Collection");
        subjectMap.put("confirmReturn","Confirmation of Pass(es) Returned");
        subjectMap.put("remindCollect","Reminder to collect Pass(es)");
        subjectMap.put("remindReturn","Reminder to return Pass(es)");
        subjectMap.put("HRLostCard","To HR: Lost Card");

        String attachmentType = "";
        String attachmenthtml = "";
        if(template.equals("confirmLoanDigital")){
            attachmentType = "corporateAttachment";
            attachmenthtml = "SAMPLENEWCorporateLetterTemplateCFOZP_002_";
        } else {
            attachmentType = "authorisationAttachment";
            attachmenthtml = "AuthorisationLetterPremiumCorporateFriendsoft";
        }
        
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

        // email attachment generation
        Pass[] passesArray = passList.toArray(new Pass [passList.size()]);
        //Pass p = passesArray[0];

        for (Pass p:passesArray){
            String pdfAttachment = emailUtil.generateEmailAttachment(attachmentType + "/"+ attachmenthtml,attachmentType,attraction,p,staffName,loanDate);
            System.out.println(template);
            System.out.println(pdfAttachment);
            emailUtil.sendEmailWithTemplateAttachment(mail,template,pdfAttachment);
        }

        return "SUCCESSED";
    }
}
    

