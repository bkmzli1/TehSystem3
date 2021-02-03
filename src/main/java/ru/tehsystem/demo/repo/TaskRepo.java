package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, String> {
    List<Task> findByCreator(User creator);
    List<Task> findByExecutor(User creator);
}
