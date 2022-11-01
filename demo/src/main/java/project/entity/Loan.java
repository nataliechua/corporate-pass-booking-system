package project.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="loan")
public class Loan {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade=CascadeType.MERGE
    )
    // Creates a foreign key in Loan called "staff_id"
    @JoinColumn(name= "staff_id")
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "loans"})
    // @JsonIgnore
    private Staff staff;
    private String loanDate;
    private String attraction;

    // Specify a many to many relationship
    @ManyToMany(
        cascade=CascadeType.MERGE,
        fetch = FetchType.EAGER
    )
    // JPA will automatically create a new association table
    // Specify the details of an association table called loan_pass
    // Many to Many: Loan_Pass will contain loanId which is a FK to loan table
    // And passId which is a FK to pass table
    @JoinTable(
        name="loan_pass",
        joinColumns=@JoinColumn(name="loan_id"),
        inverseJoinColumns=@JoinColumn(name="pass_id")
    )
    // Many to Many rs requires use of set
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "loans"}) 
    // @JsonIgnore
    private Set<Pass> passList;
    
    private String loanStatus;

    public Loan(String loanDate, String attraction) {
        this.loanDate = loanDate;
        this.attraction = attraction;
        this.loanStatus = "not collected";
        this.passList = new HashSet<Pass>();
    };

    // Helper methods to update both ends of the bidirectional many-to-many relationship
    // Between loan and pass
    public void addPasses(Set<Pass> passes) {
        for (Pass p : passes) {
            this.passList.add(p);
            p.getLoans().add(this);
        }
    }

    public void removePasses(Set<Pass> passes) {
        for (Pass p : passes) {
            this.passList.remove(p);
            p.getLoans().remove(this);
        }
    }
    
}
