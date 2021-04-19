package ru.tehsystem.demo.controller;


import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Notifications;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.Views;
import ru.tehsystem.demo.model.UserEditBindingModel;
import ru.tehsystem.demo.model.UserRegisterBindingModel;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;


    private final UserRepo userRepository;
    private final ImgRepo imgRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepo userRepository, ImgRepo imgRepository,
                          ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.imgRepository = imgRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @JsonView(Views.UserAll.class)
    @PostMapping(value = "/reg")
    @ResponseBody
    public Map<Object, Object> registerConfirm(@RequestBody @Valid UserRegisterBindingModel userRegisterBindingModel,
                                               BindingResult bindingResult) {

        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            UserRegisterBindingModel userServiceModel = this.modelMapper
                    .map(userRegisterBindingModel, UserRegisterBindingModel.class);
            this.userService.create(userServiceModel);
            strings.put("error", null);
        }
        return strings;
    }

    @GetMapping
    @JsonView(Views.UserBasic.class)
    @RequestMapping("/user")
    public User user(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return user;
        } catch (NullPointerException npe) {

        }
        return null;

    }
    @JsonView(Views.UserAll.class)
    @GetMapping("/user/{id}")
    @ResponseBody
    public User userID(@PathVariable String id) {
        User user = userRepository.findOneById(id);
        user.getNotifications().removeIf(Notifications::isClose);
        return user;
    }
    @JsonView(Views.UserAll.class)
    @PostMapping("/save")
    @ResponseBody
    public User userSave(@RequestBody() UserRegisterBindingModel userRegisterBindingModel,
                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out
                .println(bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword()));
        ;
        if (bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword())) {
            user.setFirstName(userRegisterBindingModel.getFirstName());
            user.setMiddleName(userRegisterBindingModel.getMiddleName());
            user.setLastName(userRegisterBindingModel.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userRegisterBindingModel.getPassword()));
            userRepository.save(user);
        }
        return user;
    }
    @JsonView(Views.TaskAll.class)
    @GetMapping("/executor")
    public Set<User> userEx() {
        Set<User> users = new TreeSet<User>(Comparator.comparing(User::getUsername));
        List<User> userByRole = userRepository.findUsersByAuthoritiesAuthority("EXECUTOR");
        users.addAll(userByRole);
        return users;
    }
    @JsonView(Views.TaskAll.class)
    @GetMapping("/usersss")
    public Object user(@AuthenticationPrincipal User user) {
        return user;
    }
    @JsonView(Views.UserBasic.class)
    @GetMapping("/allusers")
    public Object allUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/edituserapi")
    public Object allUsers(@RequestBody @Valid UserEditBindingModel userEditBindingModel,
                           BindingResult bindingResult) {

        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            UserEditBindingModel userServiceModel = this.modelMapper
                    .map(userEditBindingModel, UserEditBindingModel.class);
            this.userService.edit(userServiceModel);
            strings.put("error", null);
        }
        return strings;
    }
}