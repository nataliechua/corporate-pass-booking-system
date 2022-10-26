package project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.entity.*;
import java.util.*;

@SpringBootTest
public class PassRepositoryTest {
    @Autowired
    private PassRepository passRepository;

    @Test
    public void printAllPasses() {
        List<Pass> passList = passRepository.findAll();
        System.out.println("passList = " + passList);
    }

    @Test
    public void printAvailablePassesOnADate() {
        // String date = "2020-10-07";
        // List<Object> passList = passRepository.getPassByAvailability();
        // System.out.println("passList = " + passList);
    }
}
