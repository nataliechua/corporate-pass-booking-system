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

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;


@RestController
public class Emailer {
    
    @Autowired
    SendEmail emailUtil;

    @GetMapping(path = "/sendSimpleEmail")
    public String simpleEmailTest(String receiverEmail) throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo(receiverEmail);//replace with your desired email
        mail.setFrom("nineliejasongu@gmail.com");
        mail.setSubject("random");
        emailUtil.sendSimpleEmail(mail,"hellohello");
        return "SUCCESSED";
    }

    @GetMapping(path = "/TemplateEmail")
    public String emailWithTemplateTest() throws IOException, MessagingException {
        Email mail = new Email();
        mail.setMailTo("nataliechua159gmail.com");//replace with your desired email
        mail.setFrom("testemailjava91@gmail.com");
        mail.setSubject("test test");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "valerie");
        model.put("message", "hi");
        mail.setProps(model);
        emailUtil.sendEmailWithTemplate(mail, "sampleTemplate");
        return "SUCCESSED";
    }

    @GetMapping(path = "/TemplateAttachmentEmail")
    public String emailWithAttachmentTemplateTest() throws IOException, MessagingException {
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
    

