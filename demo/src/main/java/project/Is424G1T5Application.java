package project;

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
	
	}

	// uncommment below to send email, temporarily put here till frontend has send email button
	// @EventListener(ApplicationReadyEvent.class)
	// public void triggerMail(){
	// 	service.sendSimpleEmail("testemailjava91@gmail.com", "email body", "email test");
	// }

}
