package project.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.dto.LoanRequestDTO;
import project.entity.*;
import project.util.*;

import java.util.*;

@SpringBootTest
public class BookerUtilTest {
    @Autowired
    private BookerUtil booker;

    @Test
    public void validateBookerPositiveTest() {
        // Testing passes per month
        LoanRequestDTO l = new LoanRequestDTO("2022-11-25", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-11-25", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest() {
        // Testing passes per month
        LoanRequestDTO l = new LoanRequestDTO("2022-10-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-10-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest2() {
        // Testing date range
        LoanRequestDTO l = new LoanRequestDTO("2022-01-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                2,
                                1L);

        System.out.println(booker.validate("2022-01-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest3() {
        // Testing no. of available passes
        LoanRequestDTO l = new LoanRequestDTO("2022-12-11", 
                                "Singapore Flyer", 
                                "Singapore Flyer",
                                2,
                                1L);

        System.out.println(booker.validate("2022-12-11", 
        "Singapore Flyer", 
        "Singapore Flyer",
        2,
        1L));
    }

    @Test
    public void validateBookerNegativeTest4() {
        // Testing no of passes <= 2
        LoanRequestDTO l = new LoanRequestDTO("2022-01-23", 
                                "Mandai Wildlife Reserve", 
                                "Singapore Zoo",
                                3,
                                1L);

        System.out.println(booker.validate("2022-01-23", 
        "Mandai Wildlife Reserve", 
        "Singapore Zoo",
        3,
        1L));
    }
}





