package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
}
