package ru.tehsystem.demo.services.service;


import org.springframework.stereotype.Service;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.domain.Notifications;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.enums.Level;
import ru.tehsystem.demo.model.TaskCreate;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.repo.NotificationsRepo;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.TaskService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final ImgRepo imgRepo;
    private final UserRepo userRepo;
    private final NotificationsRepo notificationsRepo;

    public TaskServiceImpl(TaskRepo taskRepo, ImgRepo imgRepo, UserRepo userRepo,
                           NotificationsRepo notificationsRepo) {
        this.taskRepo = taskRepo;
        this.imgRepo = imgRepo;
        this.userRepo = userRepo;
        this.notificationsRepo = notificationsRepo;
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
        for (User userNot : task.getExecutor()) {
            Notifications notifications = new Notifications();
            notifications.setTaskId(task);
            notifications.setLevel(task.getLevel());
            notifications.setText("Создано для вас задание.\nНазвание \"" + task.getName() + "\"");
            notifications.setData(LocalDateTime.now());
            try {
                userNot.getNotifications().add(notifications);
                notificationsRepo.save(notifications);
                userRepo.save(userNot);
            } catch (Exception e) {
                userNot.setNotifications(new HashSet<Notifications>());
                Set<Notifications> notificationsAdd = userNot.getNotifications();
                notificationsAdd.add(notifications);
                notificationsRepo.save(notifications);
                userRepo.save(userNot);
            }


        }
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

    @Override public Task taskDelete(Task task, User user) {

        task.setDoneCrate(false);
        task.setPerformed(null);
        task.setDone(false);
        task.setDeletes(true);
        taskRepo.save(task);
        return null;
    }
}
