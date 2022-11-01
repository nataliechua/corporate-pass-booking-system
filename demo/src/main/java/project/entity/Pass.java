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

    public Pass(String passType, String attractions, int peoplePerPass, String isDigital, String digitalPath, String passStartDate, String passExpiryDate, float replacementFee, String isPassActive) {
        this.passType = passType;
        this.attractions = attractions;
        this.peoplePerPass = peoplePerPass;
        this.isDigital = isDigital;
        this.digitalPath = digitalPath;
        this.passStartDate = passStartDate;
        this.passExpiryDate = passExpiryDate;
        this.replacementFee = replacementFee;
        this.isPassActive = isPassActive;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pass) {
            Pass p = (Pass) o;
            return passId == p.passId;
        }
    
        return false;
    }
    // private String loans;

    @ManyToMany(mappedBy = "passList", fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "passList"})
    @JsonIgnore
    @ToString.Exclude
    private Set<Loan> loans = new HashSet<Loan>();

    public String[] getAttractionList() {
        return this.attractions.split(", ", -1);
    }
    
}
