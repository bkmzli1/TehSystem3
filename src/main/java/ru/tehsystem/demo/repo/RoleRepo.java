package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.enums.Role;
import ru.tehsystem.demo.domain.Roles;

public interface RoleRepo extends JpaRepository<Roles, String> {
    Roles findByAuthority(Role authority);
}
