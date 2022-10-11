package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    
}
