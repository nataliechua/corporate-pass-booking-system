package project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import project.controller.*;
import project.entity.*;
import project.repository.*;
import project.service.*;
import project.EmailerUtil.*;

// @SpringBootTest(classes={Loan.class, Pass.class, Staff.class})
@SpringBootTest()
public class StaffUpdateControllerTest {

    @Autowired
    private StaffUpdateController staffUpdateController;

    @Test
    public void testUpdateAdminRights() {
        staffUpdateController.updateAdminStuff(4L, "addAdmin");
    }

}

    
