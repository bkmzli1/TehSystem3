package ru.tehsystem.demo.services.impl;


import ru.tehsystem.demo.model.RoleServiceModel;

public interface RoleService {

    RoleServiceModel findByAuthority(String authority);

    void addRole(RoleServiceModel roleServiceModel);
}
