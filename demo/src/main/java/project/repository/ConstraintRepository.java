package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import project.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

@Repository
public interface ConstraintRepository extends JpaRepository<Constraint, Long>  {
    Constraint findByConstraintName(String name);
}
