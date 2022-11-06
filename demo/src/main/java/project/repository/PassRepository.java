package project.repository;

import org.springframework.stereotype.Repository;

import project.entity.*;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {

    // @Query(
    //     "From Pass where loan.loan_date = :date"
    // )
    // List<Pass> getPassByAvailability(String date);

    List<Pass> findByAttractionsContaining(String attraction);

    List<Pass> findByLoansLoanDate(String date);

    @Query("SELECT DISTINCT p from Pass p where p.isPassActive <> 'FALSE'")
    List<Pass> findActivePasses();

    // @Query("SELECT DISTINCT p from Pass p LEFT JOIN p.loans l where p.passType = :passType AND p.isPassActive <> 'FALSE' AND (l.loanDate <> :loanDate OR l.loanDate IS NULL)")
    // List<Pass> findAvailablePassesForPassTypeAndDate(@Param("passType") String passType, @Param("loanDate") String loanDate);

    // @Query("SELECT DISTINCT p from Pass p LEFT JOIN p.loans l where p.isPassActive <> 'FALSE' AND (l.loanDate <> :loanDate OR l.loanDate IS NULL)")
    // List<Pass> findAvailablePassesForADate(@Param("loanDate") String loanDate);

    @Query(value="select * from pass p where p.is_pass_active <> 'FALSE' and p.pass_id not in (select p.pass_id from pass p inner join loan_pass lp on lp.pass_id = p.pass_id inner join loan l on lp.loan_id = l.loan_id where l.loan_date = :loanDate)", nativeQuery=true)
    List<Pass> findAvailablePassesForADate(@Param("loanDate") String loanDate);

    @Query(value="select * from pass p where p.is_pass_active <> 'FALSE' and p.pass_type = :passType and p.pass_id not in (select p.pass_id from pass p inner join loan_pass lp on lp.pass_id = p.pass_id inner join loan l on lp.loan_id = l.loan_id where l.loan_date = :loanDate)", nativeQuery=true)
    List<Pass> findAvailablePassesForPassTypeAndDate(@Param("passType") String passType, @Param("loanDate") String loanDate);

    @Query("SELECT DISTINCT l from Pass p JOIN p.loans l where p = :pass AND l.loanDate = :loanDate")
    Loan findLoanForAPassAndDate(@Param("pass") Pass pass, @Param("loanDate") String loanDate);

    @Query("SELECT DISTINCT p from Pass p JOIN p.loans l where l.loanDate = :loanDate")
    List<Pass> findPassesLoanedOnADate(@Param("loanDate") String loanDate);


    // @Query("select p from pass p inner join p.loans loan where loan.loanDate = ?1 and p.passId = ?2")
    // List<Pass> findByLoanDateAndPass(String date, Long id); 
}

// "select p.* from pass p inner join loan on loan.pass_id=p.pass_id where loan.loan_date = ?1"