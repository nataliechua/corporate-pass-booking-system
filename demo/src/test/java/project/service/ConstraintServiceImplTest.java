package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.entity.*;
import java.util.*;

@SpringBootTest
public class ConstraintServiceImpl {
    @Autowired
    private ConstraintServiceImpl constraintServiceImpl;

    @Test
    public List<Constraint> getAllConstraint() {
        List<Constraint> constraintList = constraintServiceImpl.getAllConstraint();
        System.out.println(constraintList);
    }

    // @Test
    // public void getConstraintById(Long constraintId) {
    //     Optional<Constraint> constraint = constraintRepository.findById(constraintId); 
    //     return constraint.get();
    // }

    // @Test
    // public void updateConstraint(Constraint constraint) {
    //     Constraint constraintDB = constraintRepository.findById(constraint.getConstraintId()).get();

    //     // Check if parameters are null
    //     if (Objects.nonNull(constraint.getConstraintData())) {
    //         constraintDB.setConstraintData(constraint.getConstraintData());
    //     }

    //     return constraintRepository.save(constraintDB);
    // }

    // @Test
    // public void updateConstraintById(Long constraintId, Constraint constraint) {
    //     Constraint constraintDB = constraintRepository.findById(constraintId).get();

    //     // Check if parameters are null
    //     if (Objects.nonNull(constraint.getConstraintData())) {
    //         constraintDB.setConstraintData(constraint.getConstraintData());
    //     }

    //     return constraintRepository.save(constraintDB);
    // }
}
