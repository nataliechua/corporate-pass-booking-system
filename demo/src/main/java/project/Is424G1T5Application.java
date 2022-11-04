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


// @ComponentScan(basePackages ={"controller", "entity", "repository", "service"})
// @EnableJpaRepositories("repository")
@SpringBootApplication
public class Is424G1T5Application {


	// to send email upon app running, need to have frontend to send email first to link this functionality there
	// @Autowired
	// private EmailSenderService service;

	public static void main(String[] args) {
		SpringApplication.run(Is424G1T5Application.class, args);
		try{
			System.out.println("going to send email ");
			// simpleEmailTest();
			// System.out.println("sent email ");
		}catch(Exception e){
			System.out.println("there was an error?");
			e.getMessage();
		}
		System.out.println("hi");
	
	}

	// uncommment below to send email, temporarily put here till frontend has send email button
	// @EventListener(ApplicationReadyEvent.class)
	// public void triggerMail(){
	// 	service.sendSimpleEmail("testemailjava91@gmail.com", "email body", "email test");
	// }

	public static String simpleEmailTest() throws IOException, MessagingException {
		try{

			Email mail = new Email();
			mail.setMailTo("testemailjava91@gmail.com");
			mail.setFrom("nineliejasongu@gmail.com");
			mail.setSubject("email.getSubject");
			// emailer.sendSimpleEmail(mail,"getMessage");
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
        return "SUCCESSED";
    }

	public static String emailWithTemplateTest() throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo("testemailjava91@gmail.com");//replace with your desired email
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject("test test");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "valerie");
        model.put("message", "hi");
        mail.setProps(model);
		Emailer SendNew = new Emailer();
        SendNew.sendEmailWithTemplate(mail, "test-email-template");
        return "SUCCESSED";
    }

}
