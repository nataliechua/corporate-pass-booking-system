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
@Table(name="pass")
public class Pass {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    
    @Override
    public String toString() {
        return "Pass [passId=" + passId + ", passType=" + passType + ", attractions=" + attractions + ", peoplePerPass="
                + peoplePerPass + ", isDigital=" + isDigital + ", digitalPath=" + digitalPath + ", passStartDate="
                + passStartDate + ", passExpiryDate=" + passExpiryDate + ", replacementFee=" + replacementFee
                + ", isPassActive=" + isPassActive + "]";
    }
    
}
