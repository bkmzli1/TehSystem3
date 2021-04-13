package ru.tehsystem.demo.services.service;


import org.springframework.stereotype.Service;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.enums.Level;
import ru.tehsystem.demo.model.TaskCreate;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.TaskService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final ImgRepo imgRepo;
    private final UserRepo userRepo;

    public TaskServiceImpl(TaskRepo taskRepo, ImgRepo imgRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.imgRepo = imgRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Task taskCrate(TaskCreate taskCreate, User user) {
        Task task = new Task();
        task.setExecutor(new HashSet<>());
        taskCreate.getExecutor().forEach(s -> {
            User userById = userRepo.findUserById(s);
            task.getExecutor().add(userById);
        });
        task.setLevel(Level.valueOf(taskCreate.getLevel()));
        task.setName(taskCreate.getName());
        task.setText(taskCreate.getText());
        task.setImgs(new HashSet<>());
        task.setCreator(user);
        task.setMassages(new HashSet<>());
        task.setCrate(LocalDateTime.now());
        try {
            taskCreate.getImgs().forEach(imgStr -> {
                Optional<Img> byId = imgRepo.findById(imgStr);
                Img img = byId.get();
                task.getImgs().add(img);
            });
        } catch (NullPointerException nullPointerException) {
        }
        task.setDeletes(false);
        taskRepo.save(task);
        return task;
    }

    @Override
    public Task taskFin(Task task, User user) {
        task.setExecuted(LocalDateTime.now());
        task.setPerformed(user);
        task.setDone(true);
        taskRepo.save(task);
        return task;

    }

    @Override public Task taskFinCrate(Task task, User user, boolean fin) {
        if (fin) {
            task.setDoneCrate(true);

        } else {
            task.setDoneCrate(false);
            task.setPerformed(null);
            task.setDone(false);
        }
        taskRepo.save(task);
        return task;
    }

    @Override public Task taskDelete(Task task,User user) {

        task.setDoneCrate(false);
        task.setPerformed(null);
        task.setDone(false);
        task.setDeletes(true);
        taskRepo.save(task);
        return null;
    }
}
