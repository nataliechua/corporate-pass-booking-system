package project.entity;

import javax.persistence.Entity;

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
    
    @OneToMany(
        cascade=CascadeType.ALL,
        mappedBy="staff",
        orphanRemoval=true,
        fetch = FetchType.LAZY
    )

    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "staff"})
    @ToString.Exclude
    private List<Loan> loans = new ArrayList<>();
    
}
