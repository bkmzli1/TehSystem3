package ru.tehsystem.demo.services.impl;

import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.TaskCreate;

public interface TaskService {
    Task taskCrate(TaskCreate taskCreate, User user);
    Task taskFin(Task task, User user);
    Task taskFinCrate(Task task, User user, boolean fin);
}
