package project.service;

import java.util.*;

import project.entity.*;

public interface ConstraintService {
    public Constraint getConstraintById(Long constraintId);

    public List<Constraint> getAllConstraint();

    public Constraint updateConstraint(Constraint constraint);

    public Constraint updateConstraintById(Long constraintId, Constraint constraint);

    public Constraint getConstraintByConstraintName(String constraintName);
}
