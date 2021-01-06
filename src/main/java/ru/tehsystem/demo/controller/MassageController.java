package ru.tehsystem.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.MassageModel;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.services.impl.MassageService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/massage")
public class MassageController {
    private final TaskRepo taskRepo;
    private final MassageService massageService;

    public MassageController(TaskRepo taskRepo, MassageService massageService) {
        this.taskRepo = taskRepo;
        this.massageService = massageService;
    }


    @PostMapping
    @ResponseBody
    public Task ms(@RequestBody() @Valid MassageModel massageModel, Authentication authentication) {

        return  massageService.massageCrate(massageModel,(User) authentication.getPrincipal());
    }

}
