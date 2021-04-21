package ru.tehsystem.demo;

import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.repo.UserRepo;

import java.util.List;


@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeRestControllerIntegrationTest extends Assert {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void UserTest()
            throws Exception {
        List<User> all = userRepo.findAll();
        List<User> all2 = userRepo.findAll();
        Assertions.assertEquals(all, all2);
    }

    // write test cases here
}