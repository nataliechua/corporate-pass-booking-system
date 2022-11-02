package project.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.*;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.*;

@Entity
// @Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="staff", uniqueConstraints = @UniqueConstraint(columnNames = "staffEmail"))
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long staffId;

    @NotEmpty(message = "Name cannot be empty.")
    private String staffName;

    @NotEmpty(message = "Email cannot be empty.")
    private String staffEmail;

    @NotEmpty(message = "Contact number cannot be empty.")
    private String contactNum;
    
    @NotEmpty(message = "Password cannot be empty.")
    private String password;
    private String isAdminHold;
    private String isUserActive;
    private String staffType;

    @OneToMany(
        // cascade=CascadeType.MERGE,
        mappedBy="staff",
        orphanRemoval=true,
        fetch = FetchType.EAGER
    )
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "staff"})
    @JsonIgnore
    @ToString.Exclude
    private List<Loan> loans = new ArrayList<>();

    public Staff(String name, String email, String contact, String password, String type) {
        this.staffName = name;
        this.staffEmail = email;
        this.contactNum = contact;
        this.password = password;
        this.staffType = type;

        this.isAdminHold = "FALSE";
        this.isUserActive = "FALSE";
    }

    public List<Loan> addLoan(Loan loan) {
        this.loans.add(loan);
        return this.loans;
    }
    
}
