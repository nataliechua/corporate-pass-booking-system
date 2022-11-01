package project.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.service.*;
import project.entity.*;
import project.util.*;

import java.util.*;

@SpringBootTest
public class BookerUtilTest {
    @Test
    public void validateTest() {
        Loan l = new Loan("2022-11-01", "Singapore Flyer");

        System.out.println(BookerUtil.validate(l));
    }
}





