package project.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.entity.*;
import java.util.*;

@SpringBootTest
// @DataJpaTest // Test repository layer, database won't be impacted bc data will be flushed afterward
public class StaffRepositoryTest {
    @Autowired
    private StaffRepository staffRepository;

    @Test
    public void printAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        System.out.println("staffList = " + staffList);
    }

    @Test
    public void printAStaff() {
        Long id = 1L;
        Staff staff = staffRepository.findById(id).get();
        System.out.println("staff with id 1 = " + staff);
    }

    @Test
    public void createAStaff() {
        Staff newStaff = new Staff("Leslie Knope", 
                                "leslie.knope@sportsschool.edu.sg", 
                                "99988786", 
                                "leslieknope", 
                                "Admin");

        System.out.println(staffRepository.save(newStaff));
    }

        @Test
    public void updateAStaff() {
        Staff staff = staffRepository.findById(1L).get();
        
        staff.setIsAdminHold("TRUE");

        System.out.println(staffRepository.save(staff));
    }

    @Test
    public void printStaffPresentLoans() {
        System.out.println("GET STAFF PRESENT LOANS");
        Staff staff = staffRepository.findById(1L).get();
        List<Loan> loans = staff.getLoans();
        List<Loan> result = new ArrayList<>();

        String date = "2022-10-04";

        for (Loan l : loans) {
            String ld = l.getLoanDate();
            if ((ld).equals(date)) {
                System.out.println("loan date ld:" + ld);
                System.out.println("given date:" + date);
                result.add(l);
            }
        }
        System.out.println(loans);
    }

    // @Test
    // public void printStudentByFirstName() {
    //     List<Student> students = studentRepository.findByFirstName("Shabbir");

    //     System.out.println("students=" + students);
    // }

    // @Test
    // public void printStudentByFirstNameContaining() {
    //     List<Student> students = studentRepository.findByFirstNameContaining("Sha");

    //     System.out.println("students=" + students);
    // }

    // @Test
    // public void printStudentBasedOnGuardianName() {
    //     List<Student> students = studentRepository.findByGuardianName("Nikhil");

    //     System.out.println("students with Nikhil as Guardian=" + students);
    // }

    // @Test
    // public void printgetStudentByEmailAddress() {
    //     String fname = studentRepository.getStudentByEmailAddress("KEKE@gmail.com");

    //     System.out.println("students of specific email address=" + fname);
    // }

    // @Test
    // public void updateStudentNameByEmailId() {
    //     studentRepository.updateStudentNameByEmailId("Shabir Dawoodi", "Shabbir@gmail.com");
    // }
}
