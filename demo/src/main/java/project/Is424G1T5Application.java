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
import project.service.*;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Is424G1T5Application {
	public static void main(String[] args) {
		SpringApplication.run(Is424G1T5Application.class, args);

	}
}
