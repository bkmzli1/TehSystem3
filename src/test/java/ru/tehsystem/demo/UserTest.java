package ru.tehsystem.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.UserRegisterBindingModel;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.UserService;


@AutoConfigureMockMvc
@SpringBootTest
public class UserTest extends Assertions {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Test
    public void UserRepoTest()
            throws Exception {
        Assertions.assertNotNull(userRepo);
    }

    @Test
    public void UserServiceTest()
            throws Exception {
        Assertions.assertNotNull(userService);
    }


    @Test
    public void userCrateEditDelete() {
        crateUser();
        editUser("123");
        deleteUser();
    }

    private void crateUser(){
        User userTest = this.userRepo.findOneByUsername("Test");
        if (userTest == null) {
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("Test");
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setMiddleName("Test");
            user.setEmail("Test");
            user.setUsername("Test");
            user.setAdmin(true);
            user.setExecutor(true);
            this.userService.create(user);
        } else {
            userRepo.delete(userTest);
            crateUser();
        }
    }
    private void deleteUser(){
        User userTest = this.userRepo.findOneByUsername("Test");
        userRepo.delete(userTest);
    }
    private void editUser(String email){
        crateUser();
        User userTest = this.userRepo.findOneByUsername("Test");
        userTest.setEmail(email);
        userRepo.save(userTest);
    }

}