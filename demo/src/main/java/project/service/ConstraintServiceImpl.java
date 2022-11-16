package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import java.util.*;

@Service
public class ConstraintServiceImpl implements ConstraintService {
    @Autowired
    private ConstraintRepository constraintRepository;

    
    /** 
     * @return List<Constraint>
     */
    @Override
    public List<Constraint> getAllConstraint() {
        List<Constraint> constraint = new ArrayList<>();
        constraintRepository.findAll().forEach(constraint::add);
        return constraint;
    }

    
    /** 
     * @param constraintId
     * @return Constraint
     */
    @Override
    public Constraint getConstraintById(Long constraintId) {
        Optional<Constraint> constraint = constraintRepository.findById(constraintId); 
        return constraint.get();
    }

    
    /** 
     * @param constraint
     * @return Constraint
     */
    @Override
    public Constraint updateConstraint(Constraint constraint) {
        Constraint constraintDB = constraintRepository.findById(constraint.getConstraintId()).get();

        // Check if parameters are null
        if (Objects.nonNull(constraint.getConstraintData())) {
            constraintDB.setConstraintData(constraint.getConstraintData());
        }

        return constraintRepository.save(constraintDB);
    }

    
    /** 
     * @param constraintId
     * @param constraint
     * @return Constraint
     */
    @Override
    public Constraint updateConstraintById(Long constraintId, Constraint constraint) {
        Constraint constraintDB = constraintRepository.findById(constraintId).get();

        // Check if parameters are null
        if (Objects.nonNull(constraint.getConstraintData())) {
            constraintDB.setConstraintData(constraint.getConstraintData());
        }

        return constraintRepository.save(constraintDB);
    }

    
    /** 
     * @param constraintName
     * @return Constraint
     */
    public Constraint getConstraintByConstraintName(String constraintName) {
        return constraintRepository.findByConstraintName(constraintName);
    };
}
