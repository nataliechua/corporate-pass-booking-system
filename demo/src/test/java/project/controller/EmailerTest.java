package project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import project.controller.*;
import project.entity.*;
import project.repository.*;
import project.service.*;
import project.EmailerUtil.*;

// @SpringBootTest(classes={Loan.class, Pass.class, Staff.class})
@SpringBootTest()
public class EmailerTest {

    @Autowired
    private Emailer emailer;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PassRepository passRepository;

    
    /** 
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testConfirmCollect() throws IOException, MessagingException {
        Loan loan = loanRepository.findById(1L).get();
        String ans = emailer.emailWithTemplate("confirmCollect",loan);
        System.out.println(ans);
    }

    
    /** 
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testConfirmReturn() throws IOException, MessagingException {
        Loan loan = loanRepository.findById(1L).get();
        String ans = emailer.emailWithTemplate("confirmReturn",loan);
        System.out.println(ans);
    }

    
    /** 
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testRemindCollect() throws IOException, MessagingException {
        Loan loan = loanRepository.findById(1L).get();
        String ans = emailer.emailWithTemplate("remindCollect",loan);
        System.out.println(ans);
    }

    
    /** 
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testRemindReturn() throws IOException, MessagingException {
        Loan loan = loanRepository.findById(1L).get();
        String ans = emailer.emailWithTemplate("remindReturn",loan);
        System.out.println(ans);
    }

    
    /** 
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testHRLostCard() throws IOException, MessagingException {
        Pass pass= passRepository.findById(1L).get();
        Loan loan = loanRepository.findById(1L).get();
        String ans = emailer.emailWithTemplate("HRLostCard",loan,pass);
        System.out.println(ans);
    }

    

    
}

    

