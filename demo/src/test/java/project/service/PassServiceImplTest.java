package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.entity.*;
import java.util.*;

@SpringBootTest()
public class PassServiceImplTest {
    @Autowired
    private PassServiceImpl passServiceImpl;

    // @Test
    // public void saveStudent() {
    //     Guardian guardian = new Guardian("Nikhil", "Nikhil@gmail.com", "9999999");
    //     Student student = new Student("wep", "Test", guardian, "wepg@gmail.com");

    //     studentRepository.save(student);
    // }

    @Test
    public void printTotalPassNum() {
        Map<String, Integer> map = passServiceImpl.getTotalPassNum();

        System.out.println(map);
    }

    @Test
    public void printTotalPassNumByDate() {
        String date = "2022-10-07";
        Map<String, Integer> map = passServiceImpl.getPassAvailabilityByDate(date);

        System.out.println(map);
    }

    @Test
    public void printPassAvailabilityByDate() {
        Map<String, Integer> map = passServiceImpl.getPassAvailabilityByDate("2022-10-03");
        System.out.println(map);
    }


}