package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import project.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // @Query(value="select pass.pass_id, pass.pass_type, pass.attractions, pass.people_per_pass, pass.is_digital, pass.digital_path, pass.pass_start_date, pass.pass_expiry_date, pass.replacement_fee, pass.is_Pass_Active from loan l inner join pass p where l.loan_date=?1", nativeQuery=true)
    // @Query("SELECT * from loan where loan.loan_date = ?1")
    // List<Pass> findAvailablePassesByDate(String date);

    @Query("SELECT DISTINCT l from Loan l where l.loanDate = :loanDate AND l.loanStatus <> 'canceled'")
    List<Loan> findByLoanDate(@Param("loanDate") String loanDate);

    List<Loan> findByStaffStaffId(Long id);

    @Query(value="select * from loan l where l.loan_status <> 'canceled' and l.staff_id=:staffId and month(l.loan_date) = month(:date)", nativeQuery=true)
    List<Loan> findByStaffAndMonth(@Param("staffId") Long staffId, @Param("date") String date);

    @Query(value="SELECT Loan_ID from loan_pass where Pass_ID = :passId", nativeQuery=true)
    List<Long> findLoanByPass(@Param("passId") Long passId);

    @Query(value="select * from loan l where l.loan_status = :loanStatus and l.loan_date < :date", nativeQuery=true)
    List<Loan> findLoansByStatusAndPassedLoanDate(@Param("loanStatus") String loanStatus, @Param("date") String date);

    @Query(value="select * from loan l where l.loan_status = :loanStatus and l.loan_date = :date", nativeQuery=true)
    List<Loan> findLoansByStatusAndDate(@Param("loanStatus") String loanStatus, @Param("date") String date);

    List<Loan> findByLoanStatusNot(String loanStatus);

    @Modifying
    @Transactional
    @Query("update Loan l set l.loanStatus=:loanStatus WHERE l.loanId=:loanId")
    int updateLoanStatus(@Param("loanStatus") String loanStatus, @Param("loanId") Long loanId);

    // @Query("SELECT l FROM Loan l WHERE l.loanDate <> :loanDate")
    // List<Loan> findByLoanDateNotDate(@Param("loanDate") String date);

    // List<Pass> findPassesByAttractionAndDate(String date, String attraction);

    // List<Loan> findByAttraction(String attraction);


}
