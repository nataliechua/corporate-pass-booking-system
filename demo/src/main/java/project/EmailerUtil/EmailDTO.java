package project.EmailerUtil;


public class EmailDTO {
    private String name;
    private String message;
    private String email;
    private String subject;

    
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
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getMessage() {
        return message;
    }

    
    /** 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
