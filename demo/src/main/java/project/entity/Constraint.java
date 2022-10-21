package project.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data // to allow class to use Lombok. Defines getters, setters, hashcode, toString and equals
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="constraints")
public class Constraint {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long constraintId;

    private String constraintName; 

    private int constraintData; 
}
