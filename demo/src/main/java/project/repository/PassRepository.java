package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {

    // @Query(
    //     "From Pass where loan.loan_date = :date"
    // )
    // List<Pass> getPassByAvailability(String date);

}

// "select p.* from pass p inner join loan on loan.pass_id=p.pass_id where loan.loan_date = ?1"