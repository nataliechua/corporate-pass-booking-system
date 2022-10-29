package project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.entity.*;
import java.util.*;

@SpringBootTest()
public class ConstraintRepositoryTest {
    @Autowired
    private ConstraintRepository constraintRepository;

    @Test
    public void printAllConstraint() {
        List<Constraint> constraintList = constraintRepository.findAll();
        System.out.println("constraintList = " + constraintList);
    }

    @Test
    public void printAConstraint() {
        Long id = 3L;
        Constraint constraint = constraintRepository.findById(id).get();
        System.out.println("constraint with id 1 = " + constraint);
    }

    @Test
    public void updateAConstraint() {
        Constraint constraint = constraintRepository.findById(1L).get();
        
        constraint.setConstraintData(5);

        System.out.println(constraintRepository.save(constraint));
    }
}
