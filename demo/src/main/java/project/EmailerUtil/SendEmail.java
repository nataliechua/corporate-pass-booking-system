package project.EmailerUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeBodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

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
import org.apache.commons.io.IOUtils;
import javax.faces.application.Resource;
import java.util.Base64;
import org.springframework.core.io.UrlResource;

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

    public void sendEmailWithTemplateAttachment(Email mail, String template,String attachment) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
                Context context = new Context();
                context.setVariables(mail.getProps());
                
        String html = textTemplateEngine.process(template, context);

        String attachFile = "demo/src/main/resources/PDFs/" + attachment;
        helper.addAttachment(attachment, new File(attachFile));

        helper.setTo(mail.getMailTo());
        helper.setText(html);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        // message.setContent(html, "text/html");
        emailSender.send(message);
    }

    public String generateCorporateEmailAttachment(String template,String attachmentType) throws IOException {
        Context context = new Context();
        if(attachmentType.equals("corporateAttachment")){

            for(int i = 1; i <= 3 ;i++){
                Path path = Paths.get("demo/src/main/resources/templates/" + attachmentType+ "/images/image" + i + ".png");
                String base64Image = convertToBase64(path);
                String image = "data:image/png;base64," + base64Image;
                context.setVariable("image" + i,image);
            }
        }else if(attachmentType.equals("authorisationAttachment")){
            for(int i = 1; i <= 2 ;i++){
                Path path = Paths.get("demo/src/main/resources/templates/" + attachmentType+ "/images/image" + i + ".png");
                String base64Image = convertToBase64(path);
                String image = "data:image/png;base64," + base64Image;
                context.setVariable("image" + i,image);
            }
        }

        String html = htmlTemplateEngine.process(template, context);
        String FileName = generatePdfReportAsPDF(html,attachmentType);
        return FileName;
    }

    private String convertToBase64(Path path) {
        byte[] imageAsBytes = new byte[0];
        try {
          UrlResource resource = new UrlResource(path.toUri());
          System.out.println(resource);
          InputStream inputStream = resource.getInputStream();
          System.out.println("\n gg to image as bytes");
          imageAsBytes = IOUtils.toByteArray(inputStream);

    
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("\n File read Exception");
        }
    
        return Base64.getEncoder().encodeToString(imageAsBytes);
      }

    
  public String generatePdfReportAsPDF(String reportAsHtml,String attachmentType) {
    ITextRenderer renderer = new ITextRenderer();
    

    // if you have html source in hand, use it to generate document object
    try{
        renderer.setDocumentFromString(reportAsHtml);

        renderer.layout();
    }catch(Exception e){
        
        System.out.println(e.getMessage());
        
    }
    
    String fileNameWithPath = "demo/src/main/resources/PDFs/" + attachmentType + ".pdf";
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
    String[] parts = fileNameWithPath.split("/");
    String LastPart = parts[parts.length-1];
    System.out.println( "File : '" + fileNameWithPath + "' created." );
    return LastPart;
}
}
