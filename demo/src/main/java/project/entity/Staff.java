package project.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
@NoArgsConstructor
@AllArgsConstructor
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long staffId;
    private String staffName;
    private String staffEmail;
    private String contactNum;
    private String password;
    private String isAdminHold;
    private String isUserActive;
    private String staffType;    
    
    // public Staff() {

    // }
    
    // public Staff(String name, String email, String contactNum, String password, String isAdminHold,
    //         String isUserActive, String staffType) {
    //     this.staffName = name;
    //     this.staffEmail = email;
    //     this.contactNum = contactNum;
    //     this.password = password;
    //     this.isAdminHold = isAdminHold;
    //     this.isUserActive = isUserActive;
    //     this.staffType = staffType;
    // }

    @Override
    public String toString() {
        return "Staff [staffId=" + staffId + ", name=" + staffName + ", email=" + staffEmail + ", contactNum=" + contactNum
                + ", password=" + password + ", isAdminHold=" + isAdminHold + ", isUserActive=" + isUserActive
                + ", staffType=" + staffType + "]";
    }

    
    
}
