package project;

import java.util.*;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.*;
// import org.springframework.data.jpa.repository.config.*;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.*;
import org.springframework.context.annotation.*;
import project.service.*;
import project.entity.*;
import java.util.Date;
import project.EmailerUtil.*;
import java.text.SimpleDateFormat;  
import java.io.*;
import java.util.Calendar.*;

@Configuration
@EnableScheduling
public class EmailConfig {
	@Autowired
	private Emailer emailer;
    @Autowired
    private LoanService loanService;

	// This method will be triggered 9am every single day
	@Scheduled(cron = "0 0 9 * * *")
	// @Scheduled(cron = "*/2 * * * * *")
	public void scheduleEmailReminderToReturnPass() {
		System.out.println("Sending email now. Current time: " + new Date());

		Date currentDate = new Date();
		String strCurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate); 

		List<Loan> loansToRemindBorrowerToReturnPass = loanService.getLoansByStatusAndPassedLoanDate("collected", strCurrentDate);

		System.out.println(loansToRemindBorrowerToReturnPass);

		for (Loan l : loansToRemindBorrowerToReturnPass) {
			Staff s = l.getStaff();

			String staffEmail = s.getStaffEmail();

			System.out.println(staffEmail);

			System.out.println("Sending email to: " + staffEmail);
		
			try {
				System.out.println(emailer.simpleEmail(staffEmail));
			} catch (IOException | MessagingException e) {
				System.out.println("There was an exception: " + e.getMessage());
			}
		}
	}

	// This method will be triggered 9am every single day
	@Scheduled(cron = "0 0 9 * * *")
	public void scheduleEmailReminderToCollectPass() {
		System.out.println("Sending email now. Current time: " + new Date());

		Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        Date loanDate = calendar.getTime();

		String strLoanDate = new SimpleDateFormat("yyyy-MM-dd").format(loanDate); 

		List<Loan> loansToRemindBorrowerToCollectPass = loanService.getLoansByStatusAndPassedLoanDate("not collected", strLoanDate);

		for (Loan l : loansToRemindBorrowerToCollectPass) {
			Staff s = l.getStaff();

			String staffEmail = s.getStaffEmail();
			System.out.println("Sending email to: " + staffEmail);
		
			try {
				System.out.println(emailer.simpleEmail(staffEmail));
			} catch (IOException | MessagingException e) {
				System.out.println("There was an exception: " + e.getMessage());
			}
		}
	}

	
}
