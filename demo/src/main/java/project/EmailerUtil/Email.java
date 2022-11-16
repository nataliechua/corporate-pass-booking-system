package project.EmailerUtil;
import java.util.Map;

public class Email {
    private String from;
    private String mailTo;
    private String subject;
    private Map<String, Object> props;

    public Email() {}

    
    /** 
     * @return String
     */
    public String getFrom() {
        return from;
    }

    
    /** 
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    
    /** 
     * @return String
     */
    public String getMailTo() {
        return mailTo;
    }

    
    /** 
     * @param mailTo
     */
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    
    /** 
     * @return String
     */
    public String getSubject() {
        return subject;
    }

    
    /** 
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    /** 
     * @return Map<String, Object>
     */
    public Map<String, Object> getProps() {
        return props;
    }

    
    /** 
     * @param props
     */
    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

}