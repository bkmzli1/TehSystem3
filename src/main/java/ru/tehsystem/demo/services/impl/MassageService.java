package ru.tehsystem.demo.services.impl;

import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.MassageModel;

public interface MassageService {
   Task massageCrate(MassageModel massageModel, User principal);

}
