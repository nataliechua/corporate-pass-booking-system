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

}
