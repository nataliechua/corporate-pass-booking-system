package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.entity.*;
import project.dto.*;

import java.util.*;

@SpringBootTest()
public class PassServiceImplTest {
    @Autowired
    private PassServiceImpl passServiceImpl;
    
    @Test
    public void printTotalPassNum() {
        Map<String, Integer> map = passServiceImpl.getTotalPassNum();

        System.out.println(map);
    }

    @Test
    public void printTotalPassNumByDate() {
        String date = "2022-10-07";
        Map<String, Integer> map = passServiceImpl.getMapOfPassAvailabilityByDate(date);

        System.out.println(map);
    }

    @Test
    public void printPassAvailabilityByDate() {
        List<Pass> result = passServiceImpl.getAvailablePassesForPassTypeAndDate("Mandai Wildlife Reserve", "2022-10-03");
        System.out.println(result);
    }

    @Test
    public void print() {
        Map<String, PassDTO> m = passServiceImpl.getPassTypeInfoWithAvailableAndTotalCount("2022-12-11");
        System.out.println(m);
    }

}