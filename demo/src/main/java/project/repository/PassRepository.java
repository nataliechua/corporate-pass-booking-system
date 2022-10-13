package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {
    
}
