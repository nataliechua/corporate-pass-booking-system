package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // @Query(value="select pass.pass_id, pass.pass_type, pass.attractions, pass.people_per_pass, pass.is_digital, pass.digital_path, pass.pass_start_date, pass.pass_expiry_date, pass.replacement_fee, pass.is_Pass_Active from loan l inner join pass p where l.loan_date=?1", nativeQuery=true)
    // @Query("SELECT * from loan where loan.loan_date = ?1")
    // List<Pass> findAvailablePassesByDate(String date);

    List<Loan> findByLoanDate(String date);

    Loan findByStaffStaffId(Long id);

    // List<Pass> findPassesByAttractionAndDate(String date, String attraction);

    // List<Loan> findByAttraction(String attraction);


}
