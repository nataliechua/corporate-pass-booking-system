package project.EmailerUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeBodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

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
import project.entity.*;

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

    
    /** 
     * @param mail
     * @param text
     * @throws MessagingException
     * @throws IOException
     */
    public void sendSimpleEmail(Email mail, String text) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(mail.getMailTo());
        helper.setText(text);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        emailSender.send(message);
    }

    
    /** 
     * @param mail
     * @param template
     * @throws MessagingException
     * @throws IOException
     */
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

    
    /** 
     * @param mail
     * @param template
     * @param attachment
     * @throws MessagingException
     * @throws IOException
     */
    public void sendEmailWithTemplateAttachment(Email mail, String template,String attachment) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
                Context context = new Context();
                context.setVariables(mail.getProps());
                
        String html = textTemplateEngine.process(template, context);

        String attachFile = "src/main/resources/PDFs/" + attachment;
        helper.addAttachment(attachment, new File(attachFile));

        helper.setTo(mail.getMailTo());
        helper.setText(html);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        // message.setContent(html, "text/html");
        emailSender.send(message);
    }

    
    /** 
     * @param template
     * @param attachmentType
     * @return String
     * @throws IOException
     */
    public String generateEmailAttachment(String template, String attachmentType, String attraction, Pass pass, String staffName, String date) throws IOException {
        Context context = new Context();
        String passId = String.valueOf(pass.getPassId());
        String passType = pass.getPassType();
        String passTypeWithoutSpaces = passType.replaceAll("\\s+","");

        //digital
        if(attachmentType.equals("corporateAttachment")){
            if (passType.equals("Mandai Wildlife Reserve")){
                int k = 1;
                for(int i = 1; i <= 3 ;i++){
                    Path path;
                    if (i==2){
                        path = Paths.get("src/main/resources/templates/barcodes/barcode_" + passId + ".png");
                    } else {
                        path = Paths.get("src/main/resources/templates/" + attachmentType+ "/images/" + passTypeWithoutSpaces + k + ".png");
                        k += 1;
                    }
                    System.out.println(path);
                    String base64Image = convertToBase64(path);

                    String image = "data:image/png;base64," + base64Image;
                    context.setVariable("image" + i,image);
                }
            } else {
                for(int i = 1; i <= 2 ;i++){
                    Path path = Paths.get("src/main/resources/templates/" + attachmentType+ "/images/" + passTypeWithoutSpaces + ".png");
                    if (i==2){
                        path = Paths.get("src/main/resources/templates/barcodes_" + passId + ".png");
                    }
                    String base64Image = convertToBase64(path);
                    String image = "data:image/png;base64," + base64Image;
                    context.setVariable("image" + i,image);
                }
            }
        //physical
        }else if(attachmentType.equals("authorisationAttachment")){
            for(int i = 1; i <= 2 ;i++){
                Path path = Paths.get("src/main/resources/templates/" + attachmentType+ "/images/image" + i + ".png");
                String base64Image = convertToBase64(path);
                String image = "data:image/png;base64," + base64Image;
                context.setVariable("image" + i,image);
            }
        }

        
        String dateString ="";
        try{
            dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        context.setVariable("passType", passType);
        context.setVariable("today", dateString);
        context.setVariable("staffName", staffName);
        context.setVariable("loanDate", date);
        context.setVariable("attraction", attraction);
        
        String html = htmlTemplateEngine.process(template, context);
        String FileName = generatePdfReportAsPDF(html,attachmentType);
        return FileName;
    }

    
    /** 
     * @param path
     * @return String
     */
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

    
  
  /** 
   * @param reportAsHtml
   * @param attachmentType
   * @return String
   */
  public String generatePdfReportAsPDF(String reportAsHtml,String attachmentType) {
    ITextRenderer renderer = new ITextRenderer();
    
    // if you have html source in hand, use it to generate document object
    try{
        renderer.setDocumentFromString(reportAsHtml);

        renderer.layout();
    }catch(Exception e){
        
        System.out.println(e.getMessage());
        
    }
    
    String fileNameWithPath = "src/main/resources/PDFs/" + attachmentType + ".pdf";
    System.out.println(fileNameWithPath);
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
