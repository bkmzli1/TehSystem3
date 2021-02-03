package ru.tehsystem.demo.services.impl;


import ru.tehsystem.demo.domain.enums.Role;
import ru.tehsystem.demo.model.RoleServiceModel;

public interface RoleService {

    RoleServiceModel findByAuthority(Role authority);

    void addRole(RoleServiceModel roleServiceModel);
}
