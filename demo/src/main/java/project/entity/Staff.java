package project.entity;

import javax.persistence.Entity;

// import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
// @NoArgsConstructor
// @AllArgsConstructor
public class Staff {
    @Id
    @SequenceGenerator(
        name="staff_sequence",
        sequenceName="staff_sequence",
        allocationSize=1
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="staff_sequence"
    )
    private Long staffId;
    private String name;
    private String email;
    private String contactNum;
    private String password;
    private String isAdminHold;
    private String isUserActive;
    private String staffType;
    
    // public String getStaffId() {
    //     return staffId;
    // }
    // public void setStaffId(String staffId) {
    //     this.staffId = staffId;
    // }
    // public String getName() {
    //     return name;
    // }
    // public void setName(String name) {
    //     this.name = name;
    // }
    // public String getEmail() {
    //     return email;
    // }
    // public void setEmail(String email) {
    //     this.email = email;
    // }
    // public String getContactNum() {
    //     return contactNum;
    // }
    // public void setContactNum(String contactNum) {
    //     this.contactNum = contactNum;
    // }
    // public String getPassword() {
    //     return password;
    // }
    // public void setPassword(String password) {
    //     this.password = password;
    // }
    // public boolean isAdminHold() {
    //     return isAdminHold;
    // }
    // public void setAdminHold(boolean isAdminHold) {
    //     this.isAdminHold = isAdminHold;
    // }
    // public boolean isUserActive() {
    //     return isUserActive;
    // }
    // public void setUserActive(boolean isUserActive) {
    //     this.isUserActive = isUserActive;
    // }
    // public String getStaffType() {
    //     return staffType;
    // }
    
    // public void setStaffType(String staffType) {
    //     this.staffType = staffType;
    // }
    
    
    
    public Staff() {

    }
    
    public Staff(String name, String email, String contactNum, String password, String isAdminHold,
            String isUserActive, String staffType) {
        this.name = name;
        this.email = email;
        this.contactNum = contactNum;
        this.password = password;
        this.isAdminHold = isAdminHold;
        this.isUserActive = isUserActive;
        this.staffType = staffType;
    }

    @Override
    public String toString() {
        return "Staff [staffId=" + staffId + ", name=" + name + ", email=" + email + ", contactNum=" + contactNum
                + ", password=" + password + ", isAdminHold=" + isAdminHold + ", isUserActive=" + isUserActive
                + ", staffType=" + staffType + "]";
    }

    
    
}
