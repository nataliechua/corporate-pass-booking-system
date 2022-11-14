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

// import project.EmailerUtil.SendEmail;
// import project.EmailerUtil.EmailDTO;
// import project.EmailerUtil.Email;


@RestController
public class Emailer {
    
    @Autowired
    SendEmail emailUtil;

    @GetMapping(path = "/sendSimpleEmail")
    public String simpleEmailTest() throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo("testemailjava91@gmail.com");//replace with your desired email
        mail.setFrom("nineliejasongu@gmail.com");
        mail.setSubject("random");
        emailUtil.sendSimpleEmail(mail,"hellohello");
        return "SUCCESSED";
    }

    @GetMapping(path = "/TemplateEmail")
    public String emailWithTemplateTest() throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo("testemailjava91@gmail.com");//replace with your desired email
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject("test test");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "valerie");
        model.put("message", "hi");
        mail.setProps(model);
        emailUtil.sendEmailWithTemplate(mail, "sampleTemplate");
        return "SUCCESSED";
    }
}
