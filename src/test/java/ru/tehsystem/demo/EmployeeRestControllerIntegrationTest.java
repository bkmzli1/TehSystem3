package ru.tehsystem.demo;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tehsystem.demo.repo.UserRepo;


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

    }

    // write test cases here
}