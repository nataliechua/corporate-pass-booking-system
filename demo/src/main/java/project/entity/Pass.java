package project.entity;

import javax.persistence.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.*;

@Entity
// @Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="pass")
public class Pass {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    // @JsonView({JsonViewProfiles.Loan.class, JsonViewProfiles.Pass.class, JsonViewProfiles.Staff.class })
    private Long passId;
    private String passType;
    private String attractions;
    private int peoplePerPass;
    private String isDigital;
    private String digitalPath;
    private String passStartDate;
    private String passExpiryDate;
    private float replacementFee;
    private String isPassActive;
    // private String loans;

    // @ManyToMany(mappedBy = "passList", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "passList"})
    // @ToString.Exclude
    // private Set<Loan> loans = new HashSet<Loan>();
    
}
