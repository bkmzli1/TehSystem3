package ru.tehsystem.demo;

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
public class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void UserTest()
            throws Exception {
        List<User> all = userRepo.findAll();

    }

    // write test cases here
}