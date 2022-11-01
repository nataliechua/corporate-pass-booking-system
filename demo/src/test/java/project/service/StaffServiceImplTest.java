package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.entity.*;
import java.util.*;

@SpringBootTest
public class StaffServiceImplTest {
    @Autowired
    private StaffServiceImpl staffServiceImpl;

    // @Test
    // public void saveStudent() {
    //     Guardian guardian = new Guardian("Nikhil", "Nikhil@gmail.com", "9999999");
    //     Student student = new Student("wep", "Test", guardian, "wepg@gmail.com");

    //     studentRepository.save(student);
    // }

    @Test
    public void printAllStaff() {
        List<Staff> staffList = staffServiceImpl.getAllStaff();
        System.out.println("staffList = " + staffList);
    }

    @Test
    public void updateAStaff() {
        Staff newStaff = new Staff();
        Long idToUpdate = 10L;
    }

    @Test
    public void printStaffPresentLoans() {
        List<Loan> loans = staffServiceImpl.getStaffPresentLoans(1L, "2022-10-03");
        System.out.println(loans);
    }

    @Test
    public void printStaffPastLoans() {
        List<Loan> loans = staffServiceImpl.getStaffPastLoans(1L, "2022-10-04");
        System.out.println(loans);
    }

}
