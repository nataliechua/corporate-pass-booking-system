package project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffDto {
    private String staffName;
    private String staffEmail;
    private String contactNum;
    private String password;
    private String isAdminHold;
    private String isUserActive;
    private String staffType;
}
