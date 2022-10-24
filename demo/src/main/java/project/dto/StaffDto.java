package project.dto;

import lombok.*;

// @Getter
// @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {
    private String staffName;
    private String staffEmail;
    private String contactNum;
    private String password;
    private String isAdminHold;
    private String isUserActive;
    private String staffType;

    // StaffDto() {

    // }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdminHold() {
        return isAdminHold;
    }

    public void setIsAdminHold(String isAdminHold) {
        this.isAdminHold = isAdminHold;
    }

    public String getIsUserActive() {
        return isUserActive;
    }

    public void setIsUserActive(String isUserActive) {
        this.isUserActive = isUserActive;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    @Override
    public String toString() {
        return "StaffDto [staffName=" + staffName + ", staffEmail=" + staffEmail + ", contactNum=" + contactNum
                + ", password=" + password + ", isAdminHold=" + isAdminHold + ", isUserActive=" + isUserActive
                + ", staffType=" + staffType + "]";
    }

}
