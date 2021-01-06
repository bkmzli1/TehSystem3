package ru.tehsystem.demo.services.service;

import org.springframework.stereotype.Service;
import ru.tehsystem.demo.domain.Massages;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.MassageModel;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.repo.MassagesRepo;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.services.impl.MassageService;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MassageServiceImpl implements MassageService {
    private final TaskRepo taskRepo;
    private final MassagesRepo massagesRepo;
    private final ImgRepo imgRepo;

    public MassageServiceImpl(TaskRepo taskRepo, MassagesRepo massagesRepo, ImgRepo imgRepo) {
        this.taskRepo = taskRepo;
        this.massagesRepo = massagesRepo;
        this.imgRepo = imgRepo;
    }

    @Override
    public Task massageCrate(MassageModel massageModel, User user) {
        Task task = taskRepo.findById(massageModel.getId()).get();
        Massages massages = new Massages();
        massages.setUser(user);
        massages.setDateTime(LocalDateTime.now());
        massages.setImgs(new HashSet<>());
        try {
            Arrays.stream(massageModel.getImgs()).forEach(imgS -> {
                massages.getImgs().add(imgRepo.findById(imgS).get());
            });
        } catch (NullPointerException nullPointerException) {

        }
        massages.setText(massageModel.getText());
        task.getMassages().add(massages);
        massagesRepo.save(massages);
        Set<Massages> projects = new TreeSet<>(Comparator.comparing(Massages::getDateTime));
        projects.addAll(task.getMassages()) ;
        task.setMassages(projects);
        taskRepo.save(task);

        return task;
    }
}
