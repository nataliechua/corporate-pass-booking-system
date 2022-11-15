package project.EmailerUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.springframework.core.io.ClassPathResource;
import java.nio.file.Paths;
import java.nio.file.Path;
import com.itextpdf.text.DocumentException;
import java.io.InputStream;
// import org.apache.commons.io.IOUtils;
// import javax.faces.application.Resource;

@Service
public class SendEmail {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Autowired
    private TemplateEngine textTemplateEngine;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public void sendSimpleEmail(Email mail, String text) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(mail.getMailTo());
        helper.setText(text);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        emailSender.send(message);
    }

    public void sendEmailWithTemplate(Email mail, String template) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = textTemplateEngine.process(template, context);
        helper.setTo(mail.getMailTo());
        helper.setText(html);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        // message.setContent(html, "text/html");
        emailSender.send(message);
    }

    public void sendEmailWithAttachmentTemplate(Email mail, String template) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Path path = Paths.get("src/main/resources/templates/EmailAttachment/image1.png");
        // String base64Image = convertToBase64(path);
        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = htmlTemplateEngine.process(template, context);
        generatePdfReportAsPDF(html);
        helper.setTo(mail.getMailTo());
        helper.setText(html);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        // message.setContent(html, "text/html");
        

        // emailSender.send(message);
    }

    // private String convertToBase64(Path path) {
        // byte[] imageAsBytes = new byte[0];
        // try {
        //   Resource resource = new UrlResource(path.toUri());
        //   InputStream inputStream = resource.getInputStream();
        //   imageAsBytes = IOUtils.toByteArray(inputStream);
    
        // } catch (IOException e) {
        //   System.out.println("\n File read Exception");
        // }
    
        // return Base64.getEncoder().encodeToString(imageAsBytes);
    //   }

    
  public void generatePdfReportAsPDF(String reportAsHtml) {
    ITextRenderer renderer = new ITextRenderer();
    

    // if you have html source in hand, use it to generate document object
    try{
        renderer.setDocumentFromString(reportAsHtml);

        renderer.layout();
    }catch(Exception e){
        
        
    }
    
    String fileNameWithPath = "PDF-FromHtmlString.pdf";
    try{

        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();
    }catch(FileNotFoundException e){
        System.out.println("file not found");
    }catch(DocumentException e){
        System.out.println("DOCument exception");

    }catch(IOException e){
        System.out.println("IOException exception");

    }
    
    System.out.println( "File 2: '" + fileNameWithPath + "' created." );
}
}
