package project.service;

import java.util.*;

import project.entity.*;

public interface ConstraintService {
    public Constraint getConstraintById(Long constraintId);

    public List<Constraint> getAllConstraint();

    public Constraint updateConstraintById(Long constraintId, Constraint constraint);
}
