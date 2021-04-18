package ru.tehsystem.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.Views;
import ru.tehsystem.demo.model.MassageModel;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.MassageService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/massage")
public class MassageController {
    private final TaskRepo taskRepo;
    private final MassageService massageService;
    private final UserRepo userRepo;

    public MassageController(TaskRepo taskRepo, MassageService massageService, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.massageService = massageService;
        this.userRepo = userRepo;
    }

    @JsonView(Views.TaskAll.class)
    @PostMapping
    @ResponseBody
    public Task ms(@RequestBody() @Valid MassageModel massageModel, Authentication authentication) {


        try {
            return massageService.massageCrate(massageModel, (User) authentication.getPrincipal());
        } catch (NullPointerException npe) {
            return massageService.massageCrate(massageModel, userRepo.findAll().get(0));
        }
    }

}
